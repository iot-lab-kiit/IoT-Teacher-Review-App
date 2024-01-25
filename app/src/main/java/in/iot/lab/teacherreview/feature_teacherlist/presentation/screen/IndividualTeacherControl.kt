package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.ReviewCardItem
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.TeacherDetailsHeaderCard
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation.TeacherListRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.TeacherListViewModel
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.IndividualTeacherReviewApiCall

// This is the Preview function of the Teacher Review Control Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewControl() {
    CustomAppTheme {
        IndividualTeacherControl(
            navController = rememberNavController(),
            TeacherListViewModel()
        )
    }
}

// This is the Preview function of the Teacher Review Loading Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        IndividualTeacherLoading(
            selectedTeacher = TeacherListViewModel().selectedTeacher!!
        )
    }
}

// This is the Preview function of the Teacher Review Success Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewSuccess() {
    CustomAppTheme {
        IndividualTeacherSuccess(
            reviewData = ReviewData(),
            selectedTeacher = IndividualFacultyData(
                _id = ""
            )
        )
    }
}

// This is the Preview function of the Teacher Review Failure Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewFailure() {
    CustomAppTheme {
        IndividualTeacherFailure(
            selectedTeacher = TeacherListViewModel().selectedTeacher!!,
            onClickRetry = {},
            textToShow = stringResource(R.string.failed_to_load_tap_to_retry)
        )
    }
}

/**
 * Individual Teacher Control for Review Screen
 *
 * @param navController This is kept to navigate to different Screens
 * @param myViewModel This is the [TeacherListViewModel] variable
 */
@Composable
fun IndividualTeacherControl(
    navController: NavController,
    myViewModel: TeacherListViewModel
) {

    // Checking which state my app is in Currently
    when (myViewModel.individualTeacherReviewApiCall) {
        is IndividualTeacherReviewApiCall.Initialized -> {}
        is IndividualTeacherReviewApiCall.Loading -> {

            // Showing the Loading Screen
            IndividualTeacherLoading(
                selectedTeacher = myViewModel.selectedTeacher!!
            )
        }
        is IndividualTeacherReviewApiCall.Success -> {

            // Taking all the review Data
            val reviewData = (myViewModel.individualTeacherReviewApiCall as
                    IndividualTeacherReviewApiCall.Success).reviewData

            //Checking if the review Data is Empty or Not
            if (reviewData.individualReviewData.isNullOrEmpty()) {

                // Calling the Failed Screen
                IndividualTeacherFailure(
                    selectedTeacher = myViewModel.selectedTeacher!!,
                    onClickRetry = { myViewModel.getIndividualTeacherReviews() },
                    textToShow = stringResource(id = R.string.dont_have_anything_to_show)
                )


            } else {
                IndividualTeacherSuccess(
                    reviewData = reviewData,
                    selectedTeacher = myViewModel.selectedTeacher!!
                )
            }
        }
        else -> {

            // Showing the Failure Data
            IndividualTeacherFailure(
                selectedTeacher = myViewModel.selectedTeacher!!,
                onClickRetry = {
                    myViewModel.getIndividualTeacherReviews(
                        myViewModel.selectedTeacher!!._id,
                    )
                },
                textToShow = stringResource(R.string.failed_to_load_tap_to_retry)
            )
        }
    }

    // Showing the Floating Action Button On the Screen clicking which will redirect to add review Screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 24.dp, bottom = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(TeacherListRoutes.AddRatingRoute.route)
            },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * This function is Used when the Screen is Loading
 *
 * @param modifier Default kept to let the parent class pass any modifications it needs
 * @param selectedTeacher This is the details of the Selected Teacher
 */
@Composable
fun IndividualTeacherLoading(
    modifier: Modifier = Modifier,
    selectedTeacher: IndividualFacultyData
) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {

        // Showing the Teacher Details
        TeacherDetailsHeaderCard(selectedTeacher = selectedTeacher)

        // Spacer of Height 16 dp
        Spacer(modifier = Modifier.height(16.dp))


        // Showing the Progress Bar
        Box(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

/**
 * This function is Used when the Screen is Success
 *
 * @param reviewData This is the review data of the particular review
 * @param selectedTeacher This is the selected Teacher
 */
@Composable
fun IndividualTeacherSuccess(
    reviewData: ReviewData,
    selectedTeacher: IndividualFacultyData
) {

    // Lazy Column to Show the List of Reviews
    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp, end = 16.dp, start = 16.dp, bottom = 44.dp),
    ) {
        items(reviewData.individualReviewData!!.size + 1) {
            val itemCount = it - 1

            // Drawing the Header of the Teacher with his overall stats
            if (itemCount == -1) {
                TeacherDetailsHeaderCard(
                    selectedTeacher = selectedTeacher
                )

                // Spacer of Height 16 dp
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                val reviewItem = reviewData.individualReviewData[itemCount]
                ReviewCardItem(
                    createdBy = reviewItem.createdBy,
                    review = reviewItem.review!!
                )

                // Spacer of Height 16 dp
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 *  This Screen is used when the Request is a Failure
 *
 * @param modifier Default modifier to pass modifications from the Parent
 * @param selectedTeacher This is the selected Teacher
 * @param onClickRetry This is the function which is run when the teacher clicks retry
 * @param textToShow This text is shown on the Screen
 */
@Composable
fun IndividualTeacherFailure(
    modifier: Modifier = Modifier,
    selectedTeacher: IndividualFacultyData,
    onClickRetry: () -> Unit,
    textToShow: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Drawing the Header of the Teacher with his overall stats
        TeacherDetailsHeaderCard(selectedTeacher = selectedTeacher)

        // Spacer of Height 16 dp
        Spacer(modifier = Modifier.height(16.dp))

        // This is a text Button which says to Try Again
        TextButton(
            onClick = { onClickRetry() }
        ) {
            Text(
                text = textToShow,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}