package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HomeScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.IndividualTeacherControl
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.TeacherListViewModel

/**
 * Navigation Graph : It contains all the Different Routes in the Home Feature
 */
@Composable
fun TeacherListNavGraph(
    navController: NavHostController
) {

    // Teacher List ViewModel which will be passed down to different Functions
    val teacherListViewModel: TeacherListViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = BottomNavRoutes.HomeRoute.route,
        builder = {

            // Teacher List Main Screen
            composable(
                BottomNavRoutes.HomeRoute.route,
                content = {
                    HomeScreenControl(
                        navController = navController,
                        myViewModel = teacherListViewModel
                    )
                }
            )

            // Individual Teacher List Reviews Screen
            composable(
                TeacherListRoutes.IndividualTeacherRoute.route,
                content = {
                    IndividualTeacherControl(
                        navController = navController,
                        myViewModel = teacherListViewModel
                    )
                }
            )

            // Add Rating and Review Screen
            composable(
                TeacherListRoutes.AddRatingRoute.route,
                content = {

                    // Nav controller for Adding Review and Rating Screen
                    val addRatingNavController = rememberNavController()

                    AddRatingNavGraph(
                        navController = addRatingNavController
                    )
                }
            )
        }
    )
}