package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state_action.AddReviewAction
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.AddStarWithHeadingTitleUI
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation.TeacherListRoutes

// This is the Preview function of the Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        AddRatingScreen(
            navController = rememberNavController(),
            action = {},
            teacherName = "Teacher Name",
            markingRating = 3.0,
            attendanceRating = 4.0,
            teachingRating = 5.0
        )
    }
}

/**
 * This function draws The UI of the Add Rating Screen
 *
 * @param modifier Default to pass down modifications from the Parent
 * @param navController This is the navController which helps in Navigation
 * @param myViewModel This variable is the View Model for the Screen
 */
@Composable
fun AddRatingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    action : (AddReviewAction) -> Unit,
    teacherName : String,
    markingRating : Double,
    attendanceRating : Double,
    teachingRating : Double
) {

    // This is the Parent Composable which contains all the Components
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        // This is the Composable which contains the Card and the Add Rating Heading
        Column(
            modifier = Modifier
                .padding(bottom = 54.dp, start = 32.dp, end = 32.dp, top = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Add Rating
            Text(
                text = stringResource(R.string.add_rating),
                style = MaterialTheme.typography.headlineSmall,
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This is the card which contains all the input star fields and the main content of the Screen
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Teacher Profile Picture
                    Image(
                        painter = painterResource(id = R.drawable.profile_photo),
                        contentDescription = stringResource(id = R.string.profile),
                        modifier = Modifier
                            .size(54.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                    )

                    // Space of Height 8 dp
                    Spacer(modifier = Modifier.height(8.dp))

                    // Teacher Name
                    // TODO Need to attach the Real Name of the Teacher
                    Text(
                        text = teacherName,
                        style = MaterialTheme.typography.headlineSmall,
                    )

                    // Spacing of 16 dp
                    Spacer(modifier = Modifier.height(32.dp))

                    // This is the Marking Rating Input Field
                    AddStarWithHeadingTitleUI(
                        headingTitle = R.string.marking_rating,
                        starCount = markingRating,
                        onAddClick = { action(AddReviewAction.UpdateUserInputMarkingRating(1)) },
                        onSubtractClick = { action(AddReviewAction.UpdateUserInputMarkingRating(0)) }
                    )

                    // Spacing of 16 dp
                    Spacer(modifier = Modifier.height(16.dp))

                    // This is the Attendance Rating Input Field
                    AddStarWithHeadingTitleUI(
                        headingTitle = R.string.attendance_rating,
                        starCount = attendanceRating,
                        onAddClick = { action(AddReviewAction.UpdateUserInputAttendanceRating(1)) },
                        onSubtractClick = { action(AddReviewAction.UpdateUserInputAttendanceRating(0)) }
                    )

                    // Spacing of 16 dp
                    Spacer(modifier = Modifier.height(16.dp))

                    // This is the Teaching Rating Input Field
                    AddStarWithHeadingTitleUI(
                        headingTitle = R.string.teaching_rating,
                        starCount = teachingRating,
                        onAddClick = { action(AddReviewAction.UpdateUserInputTeachingRating(1)) },
                        onSubtractClick = { action(AddReviewAction.UpdateUserInputTeachingRating(0)) }
                    )

                    // Spacing of 24 dp
                    Spacer(modifier = Modifier.height(24.dp))

                    // This Composable contains the Button to move to the add Review Screen
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // Add your Review Text
                        Text(
                            text = stringResource(R.string.add_your_review),
                            style = MaterialTheme.typography.titleMedium,
                        )

                        // This Button takes to the Add Review Screen
                        Button(
                            onClick = {
                                navController.navigate(TeacherListRoutes.AddReviewRoute.route)
                            },
                        ) {
                            Text(
                                text = stringResource(R.string.comment),
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }
            }
        }
    }
}