package `in`.iot.lab.teacherreview.feature_teacherlist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.HomeScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.IndividualTeacherControl
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder.TeacherListViewModel

/**
 * Navigation Graph : It contains all the Different Routes in the Home Feature
 */
@Composable
fun TeacherListNavGraph(
    navController: NavHostController
) {

    // Teacher List ViewModel which will be passed down to different Functions
    val teacherListViewModel: TeacherListViewModel = hiltViewModel()

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
                        action = teacherListViewModel::action,
                        teacherListApiCallState = teacherListViewModel.teacherListApiCallState,
                    )
                }
            )

            // Individual Teacher List Reviews Screen
            composable(
                TeacherListRoutes.IndividualTeacherRoute.route,
                content = {
                    val currentUserId by teacherListViewModel.currentUserId.collectAsStateWithLifecycle()
                    IndividualTeacherControl(
                        navController = navController,
                        selectedTeacher = teacherListViewModel.selectedTeacher!!,
                        action = teacherListViewModel::action,
                        individualTeacherReviewApiCall = teacherListViewModel.individualTeacherReviewApiCall,
                        currentUserId = currentUserId,
                    )
                }
            )

            // Add Rating and Review Screen
            composable(
                TeacherListRoutes.AddRatingRoute.route,
                content = {

                    // Currently Selected Teacher Data variable
                    val teacherData = teacherListViewModel.selectedTeacher!!

                    // Nav controller for Adding Review and Rating Screen
                    val addRatingNavController = rememberNavController()

                    AddRatingNavGraph(
                        navController = addRatingNavController,
                        teacherData = teacherData,
                        refreshTeacherReviews = {
                            teacherListViewModel.getIndividualTeacherReviews()
                        }
                    )
                }
            )
        }
    )
}