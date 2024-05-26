package `in`.iot.lab.teacherreview.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.history.view.navigation.historyNavGraph
import `in`.iot.lab.profile.view.navigation.profileNavGraph
import `in`.iot.lab.review.view.navigation.REVIEW_ROUTE
import `in`.iot.lab.review.view.navigation.reviewNavGraph


const val HOME_ROOT_ROUTE = "home-root-route"

@Composable
fun HomeNavGraph(onLogOut: () -> Unit) {

    val navController = rememberNavController()

    AppScreen(
        contentAlignment = Alignment.TopStart,
        bottomBar = {
            NewBottomNavBar(
                navController = navController,
                bottomMenu = NewBottomNavOptions.bottomNavOptions
            )
        }
    ) {

        NavHost(
            navController,
            startDestination = REVIEW_ROUTE,
        ) {

            reviewNavGraph(navController)

            historyNavGraph()

            profileNavGraph(onSignOutClick = onLogOut)
        }
    }
}