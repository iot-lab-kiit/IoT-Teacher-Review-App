package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.teacherreview.feature_bottom_navigation.screens.HomeScreen

const val HOME_ROOT = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROOT, navOptions)

fun NavGraphBuilder.homeNavGraph() {
    composable(HOME_ROOT) {
        HomeScreen()
    }
}