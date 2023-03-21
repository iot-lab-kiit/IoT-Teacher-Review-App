package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HistoryScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HomeScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.ProfileScreen

// Navigation Graph : It contains all the Different Routes in the Home Feature
@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = BottomNavRoutes.HomeRoute.route,
        builder = {

            // Bottom Navigation Options
            composable(
                BottomNavRoutes.HomeRoute.route,
                content = { HomeScreen(navController = navController) }
            )
            composable(
                BottomNavRoutes.HistoryRoute.route,
                content = { HistoryScreen(navController = navController) }
            )
            composable(
                BottomNavRoutes.ProfileRoute.route,
                content = { ProfileScreen(navController = navController) }
            )
        })
}