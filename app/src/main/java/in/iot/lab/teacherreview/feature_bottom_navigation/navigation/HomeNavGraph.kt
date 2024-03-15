package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation.TeacherListNavGraph
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HistoryScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.ProfileScreen

/**
 * Navigation Graph : It contains all the Different Routes in the bottom Navigation
 */
@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = BottomNavRoutes.HomeRoute.route,
        builder = {

            // Teacher List Bottom Navigation Option
            composable(
                BottomNavRoutes.HomeRoute.route,
                content = {

                    val teacherListNavController = rememberNavController()

                    TeacherListNavGraph(
                        navController = teacherListNavController,
                    )
                }
            )

            // History Bottom Navigation Option
            composable(
                BottomNavRoutes.HistoryRoute.route,
                content = { HistoryScreenControl() }
            )

            // Profile Bottom Navigation Options
            composable(
                BottomNavRoutes.ProfileRoute.route,
                content = { ProfileScreen(navController = navController) }
            )
        }
    )
}