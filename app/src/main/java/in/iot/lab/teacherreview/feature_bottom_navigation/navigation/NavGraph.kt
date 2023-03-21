package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation.TeacherListRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HistoryScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HomeScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.IndividualTeacherControl
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.ProfileScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.TeacherListViewModel

/**
 * Navigation Graph : It contains all the Different Routes in the Home Feature
 */
@Composable
fun HomeNavGraph(navController: NavHostController) {

    // Teacher List ViewModel which will be passed down to different Functions
    val teacherListViewModel: TeacherListViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = BottomNavRoutes.HomeRoute.route,
        builder = {

            // Bottom Navigation Options
            composable(
                BottomNavRoutes.HomeRoute.route,
                content = {
                    HomeScreenControl(
                        navController = navController,
                        myViewModel = teacherListViewModel
                    )
                }
            )
            composable(
                BottomNavRoutes.HistoryRoute.route,
                content = { HistoryScreen(navController = navController) }
            )
            composable(
                BottomNavRoutes.ProfileRoute.route,
                content = { ProfileScreen(navController = navController) }
            )
            composable(
                TeacherListRoutes.IndividualTeacherRoute.route,
                content = {
                    IndividualTeacherControl(
                        navController = navController,
                        myViewModel = teacherListViewModel
                    )
                }
            )
        })
}