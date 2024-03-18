package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.AddRatingScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.AddReviewScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.AddReviewViewModel

/**
 * This function contains the Navigation Options inside the Add Review and Rating Screens
 * It is wrapped in a different nav Graph because it is going to have a shared View Model so to limit
 * the scope of the View Model to this Nav Graph We did this
 *
 * @param navController This is used for navigating through different Screens
 * @param teacherData This is the data of the current Selected Teacher
 * @param refreshTeacherReviews This function refreshes the Individual Teacher Screen
 */
@Composable
fun AddRatingNavGraph(
    navController: NavHostController,
    teacherData: IndividualFacultyData,
    refreshTeacherReviews: () -> Unit
) {

    // Shared View Model which is shared between the add review and add rating Screens
    val myViewModel: AddReviewViewModel = hiltViewModel()

    // Setting the Current Teacher Details in the View Model
    myViewModel.setTeacherId(teacherData)

    val state by myViewModel.userInputReview.collectAsState()

    NavHost(
        navController = navController,
        startDestination = TeacherListRoutes.AddRatingRoute.route,
        builder = {

            // Add Rating Screen
            composable(
                route = TeacherListRoutes.AddRatingRoute.route,
                content = {
                    AddRatingScreen(
                        navController = navController,
                        action = myViewModel::action,
                        teacherName = teacherData.name,
                        markingRating = state.markingRating,
                        attendanceRating = state.attendanceRating,
                        teachingRating = state.teachingRating
                    )
                }
            )

            // Add Review Screen
            composable(
                route = TeacherListRoutes.AddReviewRoute.route,
                content = {
                    AddReviewScreen(
                        refreshTeacherReviews = refreshTeacherReviews,
                        action = myViewModel::action,
                        addReviewApiState = myViewModel.addReviewApiState,
                        teacherName = teacherData.name,
                        overallReview = state.overallReview,
                        markingReview = state.markingReview,
                        attendanceReview = state.attendanceReview,
                        teachingReview = state.teachingReview
                    )
                }
            )
        }
    )
}