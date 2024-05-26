package `in`.iot.lab.teacherreview.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.history.view.navigation.historyNavGraph
import `in`.iot.lab.profile.view.navigation.profileNavGraph
import `in`.iot.lab.review.view.navigation.REVIEW_ROUTE
import `in`.iot.lab.review.view.navigation.reviewNavGraph
import `in`.iot.lab.teacherreview.components.BottomNavBar


const val HOME_ROOT_ROUTE = "home-root-route"


fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROOT_ROUTE, navOptions)
}


@Composable
fun HomeNavGraph(onLogOut: () -> Unit) {

    val navController = rememberNavController()

    AppScreen(
        contentAlignment = Alignment.TopStart,
        bottomBar = {
            BottomNavBar(
                navController = navController,
                bottomMenu = BottomNavOptions.bottomNavOptions
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