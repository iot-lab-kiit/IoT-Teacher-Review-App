package `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.AddReviewWithHeadingTitleUI
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.state_action.AddReviewAction
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.AddReviewApiState

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
        AddReviewScreen(
            action = {},
            refreshTeacherReviews = {},
            addReviewApiState = AddReviewApiState.Initialized,
            teacherName = "Teacher Name",
            overallReview = "",
            markingReview = "",
            attendanceReview = "",
            teachingReview = ""
        )
    }
}

/**
 * This function draws The UI of the Add Rating Screen
 *
 * @param modifier Default to pass down modifications from the Parent
 * @param myViewModel This variable is the View Model for the Screen
 * @param refreshTeacherReviews This function refreshes the Individual teacher List Screen
 */
@Composable
fun AddReviewScreen(
    modifier: Modifier = Modifier,
    action: (AddReviewAction) -> Unit,
    refreshTeacherReviews: () -> Unit,
    addReviewApiState: AddReviewApiState,
    teacherName: String,
    overallReview: String,
    navigateToTeachersScreen: () -> Unit = {},
    markingReview: String,
    attendanceReview: String,
    teachingReview: String
) {

    // Context Variable
    val context = LocalContext.current

    // Checking which api state it is and doing the things accordingly
    when (addReviewApiState) {
        is AddReviewApiState.Initialized -> {}
        is AddReviewApiState.Loading -> {

            // Circular Loading Progress Indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is AddReviewApiState.Success -> {

            Toast.makeText(
                context,
                "Review Posted",
                Toast.LENGTH_SHORT
            ).show()

            // Refreshing the Reviews in the Individual Teacher Screen
            refreshTeacherReviews()

            //Resetting everything to Default
            action(AddReviewAction.ResetToDefault)

            navigateToTeachersScreen()
        }
        else -> {

            // Failed Toast
            Toast.makeText(
                context,
                (addReviewApiState as AddReviewApiState.Failure)
                    .errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            //Resetting Api State to Default
            action(AddReviewAction.ResetApiToInitialize)

        }
    }

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

            // Add Review Header Text
            Text(
                text = stringResource(R.string.add_review),
                style = MaterialTheme.typography.headlineSmall,
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This is the card which contains all the input fields of the Review
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

                    // Overall Review
                    AddReviewWithHeadingTitleUI(
                        headingTitle = R.string.overall_review,
                        userInput = overallReview,
                        onUserInputChange = {
                            action(AddReviewAction.UpdateOverallReview(it))
                        }
                    )

//                    // Spacing of 16 dp
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Marking Review
//                    AddReviewWithHeadingTitleUI(
//                        headingTitle = R.string.marking_review,
//                        userInput = markingReview,
//                        onUserInputChange = {
//                            action(AddReviewAction.UpdateMarkingReview(it))
//                        }
//                    )
//
//                    // Spacing of 16 dp
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Attendance Review
//                    AddReviewWithHeadingTitleUI(
//                        headingTitle = R.string.attendance_review,
//                        userInput = attendanceReview,
//                        onUserInputChange = {
//                            action(AddReviewAction.UpdateAttendanceReview(it))
//                        }
//                    )
//
//                    // Spacing of 16 dp
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Teaching Review
//                    AddReviewWithHeadingTitleUI(
//                        headingTitle = R.string.teaching_review,
//                        userInput = teachingReview,
//                        onUserInputChange = {
//                            action(AddReviewAction.UpdateTeachingReview(it))
//                        }
//                    )
//
                    Spacer(modifier = Modifier.height(16.dp))

                    // This Button Adds the Review
                    Button(
                        onClick = {
                            action(AddReviewAction.PostReviewData)
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.submit_review),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
            }
        }
    }
}