package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation.TeacherListRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.AddReviewViewModel

@Composable
fun AddRatingScreen(
    navController: NavController,
    myViewModel: AddReviewViewModel
) {
    Text(
        text = "Add Rating Screen",
        modifier = Modifier
            .clickable {
                navController.navigate(TeacherListRoutes.AddReviewRoute.route)
            }
    )
}