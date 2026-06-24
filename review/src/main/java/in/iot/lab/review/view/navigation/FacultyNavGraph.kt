package `in`.iot.lab.review.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import `in`.iot.lab.design.transitions.CyberpunkTransitions
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.kritique.domain.models.review.RemoteReviewHistoryResponse
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.screens.FacultyListScreenControl
import `in`.iot.lab.review.view.screens.PostReviewScreenControl
import `in`.iot.lab.review.view.screens.ReviewDetailScreenControl
import `in`.iot.lab.review.vm.FacultyViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

const val FACULTY_ROOT_ROUTE   = "review-root-route"
internal const val FACULTY_LIST_ROUTE   = "teacher-list-route"
internal const val FACULTY_DETAIL_ROUTE = "teacher-detail-route"
const val REVIEW_POST_ROUTE    = "review-post-route"

fun NavController.navigateToFacultyDetail() {
    navigate(FACULTY_DETAIL_ROUTE) { launchSingleTop = true }
}

fun NavGraphBuilder.facultyNavGraph(
    navController: NavHostController,
    bookmarkedIds: StateFlow<Set<String>> = MutableStateFlow(emptySet()),
    onBookmarkClick: (RemoteFaculty) -> Unit = {}
) {
    navigation(
        route = FACULTY_ROOT_ROUTE,
        startDestination = FACULTY_LIST_ROUTE
    ) {

        composable(
            route = FACULTY_LIST_ROUTE,
            enterTransition     = { CyberpunkTransitions.tabEnter },
            exitTransition      = { CyberpunkTransitions.tabExit },
            popEnterTransition  = { CyberpunkTransitions.tabEnter },
            popExitTransition   = { CyberpunkTransitions.tabExit }
        ) {
            val viewModel   = it.getViewModel<FacultyViewModel>(navController)
            val facultyList = viewModel.facultyList.collectAsLazyPagingItems()
            FacultyListScreenControl(
                facultyList = facultyList,
                setEvent    = viewModel::uiListener,
                navigator   = navController::navigate
            )
        }

        composable(
            route = FACULTY_DETAIL_ROUTE,
            enterTransition     = { CyberpunkTransitions.facultyDetailEnter },
            exitTransition      = { CyberpunkTransitions.facultyDetailExit },
            popEnterTransition  = { CyberpunkTransitions.facultyDetailPopEnter },
            popExitTransition   = { CyberpunkTransitions.facultyDetailPopExit }
        ) {
            val viewModel   = it.getViewModel<FacultyViewModel>(navController)
            val reviewList  = viewModel.reviewList.collectAsLazyPagingItems()
            val facultyData = viewModel.facultyData.collectAsState().value

            // ✅ Wrapped in remember — fixes "getBackStackEntry during composition" warning
            val rootEntry = remember(it) {
                navController.getBackStackEntry(FACULTY_ROOT_ROUTE)
            }
            val savedFacultyId = rootEntry.savedStateHandle.get<String>("selected_faculty_id")

            LaunchedEffect(savedFacultyId) {
                savedFacultyId?.let { id ->
                    viewModel.uiListener(FacultyEvent.FacultySelected(id))
                    rootEntry.savedStateHandle.remove<String>("selected_faculty_id")
                }
            }

            // Collect bookmark state HERE, inside the destination's own composition,
            // so the icon updates live on toggle. Passing a snapshot from the NavHost
            // builder would capture a stale value that never changes.
            val currentBookmarkedIds by bookmarkedIds.collectAsState()

            ReviewDetailScreenControl(
                facultyData     = facultyData,
                reviewList      = reviewList,
                onFabClick      = { navController.navigate(REVIEW_POST_ROUTE) },
                onBackClick     = navController::popBackStack,
                setEvent        = viewModel::uiListener,
                bookmarkedIds   = currentBookmarkedIds,
                onBookmarkClick = onBookmarkClick
            )
        }

        composable(
            route = REVIEW_POST_ROUTE,
            enterTransition     = { CyberpunkTransitions.sheetEnter },
            exitTransition      = { CyberpunkTransitions.sheetExit },
            popEnterTransition  = { CyberpunkTransitions.facultyDetailPopEnter },
            popExitTransition   = { CyberpunkTransitions.sheetExit }
        ) {
            val viewModel     = it.getViewModel<FacultyViewModel>(navController)
            val submitState   = viewModel.reviewSubmitState.collectAsState().value
            val editingReview = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RemoteReviewHistoryResponse>("editing_review")

            LaunchedEffect(editingReview) {
                editingReview?.let { review ->
                    viewModel.startEditingReview(review)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.remove<RemoteReviewHistoryResponse>("editing_review")
                }
            }

            PostReviewScreenControl(
                submitState = submitState,
                setEvent    = viewModel::uiListener,
                goBack      = navController::popBackStack,
                viewModel   = viewModel
            )
        }
    }
}

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.getViewModel(
    navController: NavController
): VM {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry   = remember(this) { navController.getBackStackEntry(navGraphRoute) }
    return hiltViewModel(parentEntry)
}