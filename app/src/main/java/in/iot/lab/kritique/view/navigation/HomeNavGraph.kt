package `in`.iot.lab.kritique.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import `in`.iot.lab.design.components.AppScaffold
import `in`.iot.lab.design.components.LocalHazeState
import `in`.iot.lab.design.transitions.CyberpunkTransitions
import `in`.iot.lab.history.view.navigation.historyNavGraph
import `in`.iot.lab.kritique.view.vm.BookmarkViewModel
import `in`.iot.lab.profile.view.navigation.profileNavGraph
import `in`.iot.lab.review.view.navigation.FACULTY_ROOT_ROUTE
import `in`.iot.lab.review.view.navigation.REVIEW_POST_ROUTE
import `in`.iot.lab.review.view.navigation.facultyNavGraph
import `in`.iot.lab.review.view.navigation.navigateToFacultyDetail

const val HOME_ROOT_ROUTE = "home-root-route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROOT_ROUTE, navOptions)
}

@Composable
fun HomeNavGraph(onLogOut: () -> Unit) {

    val navController     = rememberNavController()
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    AppScaffold(
        contentAlignment = Alignment.TopStart,
        bottomBar = {
            val hazeState = LocalHazeState.current ?: remember { HazeState() }
            CustomBottomNavigation(navController = navController, hazeState = hazeState)
        }
    ) {
        NavHost(
            navController      = navController,
            startDestination   = FACULTY_ROOT_ROUTE,
            enterTransition    = { CyberpunkTransitions.defaultEnter },
            exitTransition     = { CyberpunkTransitions.defaultExit },
            popEnterTransition = { CyberpunkTransitions.defaultPopEnter },
            popExitTransition  = { CyberpunkTransitions.defaultPopExit }
        ) {
            facultyNavGraph(
                navController   = navController,
                bookmarkedIds   = bookmarkViewModel.bookmarkedIds,
                onBookmarkClick = { faculty -> bookmarkViewModel.toggleBookmark(faculty) }
            )

            historyNavGraph(
                onEditReview = { review ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("editing_review", review)
                    navController.navigate(REVIEW_POST_ROUTE)
                }
            )

            bookmarkNavGraph(
                onExploreClick = {
                    navController.navigate(FACULTY_ROOT_ROUTE) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                onFacultyClick = { facultyId ->
                    // ✅ Navigate to FACULTY_ROOT_ROUTE first so it's on the back stack,
                    // THEN set savedStateHandle + navigate to detail
                    navController.navigate(FACULTY_ROOT_ROUTE) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                    // Now FACULTY_ROOT_ROUTE is on stack — safe to getBackStackEntry
                    navController
                        .getBackStackEntry(FACULTY_ROOT_ROUTE)
                        .savedStateHandle["selected_faculty_id"] = facultyId
                    navController.navigateToFacultyDetail()
                }
            )

            profileNavGraph(onSignOutClick = onLogOut)
        }
    }
}