package `in`.iot.lab.review.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import `in`.iot.lab.review.view.screens.PostReviewScreenControl
import `in`.iot.lab.review.view.screens.ReviewDetailScreenControl
import `in`.iot.lab.review.view.screens.FacultyListScreenControl
import `in`.iot.lab.review.vm.FacultyViewModel


const val FACULTY_ROOT_ROUTE = "faculty-root-route"
internal const val FACULTY_LIST_ROUTE = "faculty-list-route"
internal const val FACULTY_DETAIL_ROUTE = "faculty-detail-route"
internal const val REVIEW_POST_ROUTE = "review-post-route"


fun NavGraphBuilder.facultyNavGraph(
    navController: NavHostController
) {

    navigation(
        route = FACULTY_ROOT_ROUTE,
        startDestination = FACULTY_LIST_ROUTE
    ) {


        // Teacher List Route
        composable(FACULTY_LIST_ROUTE) {

            val viewModel = it.getViewModel<FacultyViewModel>(navController)
            val facultyList = viewModel.facultyList.collectAsLazyPagingItems()

            FacultyListScreenControl(
                facultyList = facultyList,
                setEvent = viewModel::uiListener,
                navigator = navController::navigate
            )
        }

        // Teacher Review Detail screen
        composable(FACULTY_DETAIL_ROUTE) {

            val viewModel = it.getViewModel<FacultyViewModel>(navController)
            val reviewList = viewModel.reviewList.collectAsLazyPagingItems()
            val facultyData = viewModel.facultyData.collectAsState().value

            ReviewDetailScreenControl(
                facultyData = facultyData,
                reviewList = reviewList,
                onFabClick = { navController.navigate(REVIEW_POST_ROUTE) },
                onBackClick = navController::popBackStack,
                setEvent = viewModel::uiListener
            )
        }

        // Review Post Screen
        composable(REVIEW_POST_ROUTE) {

            val viewModel = it.getViewModel<FacultyViewModel>(navController)
            val submitState = viewModel.reviewSubmitState.collectAsState().value

            PostReviewScreenControl(
                submitState = submitState,
                setEvent = viewModel::uiListener,
                goBack = navController::popBackStack
            )
        }
    }
}


/**
 * This function creates a [ViewModel] scoped to the Nav Graph so that the same view Model can be
 * used across the whole app.
 *
 * @param navController This is the nav controller which can be used to get the nav graph to scope
 * the View Model to the nav Graph.
 */
@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.getViewModel(
    navController: NavController
): VM {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}