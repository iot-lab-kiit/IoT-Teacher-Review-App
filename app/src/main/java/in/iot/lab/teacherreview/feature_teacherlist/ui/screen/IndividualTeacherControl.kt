package `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.design.components.PullToRefresh
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.ReviewCardItem
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.TeacherDetailsHeaderCard
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.navigation.TeacherListRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.state_action.TeacherListAction
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder.TeacherListViewModel
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.IndividualTeacherReviewApiCall

// This is the Preview function of the Teacher Review Control Screen
@RequiresApi(Build.VERSION_CODES.O)
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
            selectedTeacher = IndividualFacultyData(
                _id = ""
            ),
            action = {},
            individualTeacherReviewApiCall = IndividualTeacherReviewApiCall.Initialized,
            currentUserId = ""

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
            selectedTeacher = IndividualFacultyData(
                _id = ""
            )
        )
    }
}

// This is the Preview function of the Teacher Review Success Screen
@RequiresApi(Build.VERSION_CODES.O)
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
            ),
            currentUserId = ""
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
            selectedTeacher = IndividualFacultyData(
                _id = ""
            ),
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
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IndividualTeacherControl(
    navController: NavController,
    selectedTeacher: IndividualFacultyData,
    individualTeacherReviewApiCall: IndividualTeacherReviewApiCall,
    currentUserId: String?,
    action: (TeacherListAction) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(TeacherListRoutes.AddRatingRoute.route)
                },
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.RateReview,
                        contentDescription = stringResource(id = R.string.add),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(text = "Add Your Review")
                }
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            when (individualTeacherReviewApiCall) {
                is IndividualTeacherReviewApiCall.Initialized -> {}
                is IndividualTeacherReviewApiCall.Loading -> {

                    // Showing the Loading Screen
                    IndividualTeacherLoading(
                        selectedTeacher = selectedTeacher,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

                is IndividualTeacherReviewApiCall.Success -> {

                    // Taking all the review Data
                    val reviewData = individualTeacherReviewApiCall.reviewData

                    //Checking if the review Data is Empty or Not
                    if (reviewData.individualReviewData.isNullOrEmpty()) {

                        // Calling the Failed Screen
                        IndividualTeacherFailure(
                            selectedTeacher = selectedTeacher,
                            onClickRetry = {
                                action(
                                    TeacherListAction.GetIndividualTeacherReviews(
                                        selectedTeacher._id
                                    )
                                )
                            },
                            textToShow = stringResource(id = R.string.dont_have_anything_to_show),
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )


                    } else {
                        IndividualTeacherSuccess(
                            loading = individualTeacherReviewApiCall is IndividualTeacherReviewApiCall.Loading,
                            reviewData = reviewData,
                            selectedTeacher = selectedTeacher,
                            onBackClick = {
                                navController.popBackStack()
                            },
                            currentUserId = currentUserId,
                            refreshReviews = {
                                action(
                                    TeacherListAction.GetIndividualTeacherReviews(
                                        selectedTeacher._id
                                    )
                                )
                            }
                        )
                    }
                }

                else -> {

                    // Showing the Failure Data
                    IndividualTeacherFailure(
                        selectedTeacher = selectedTeacher,
                        onClickRetry = {
                            action(
                                TeacherListAction.GetIndividualTeacherReviews(
                                    selectedTeacher._id
                                )
                            )
                        },
                        textToShow = stringResource(R.string.failed_to_load_tap_to_retry)
                    )
                }
            }
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
    selectedTeacher: IndividualFacultyData,
    onBackClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        // Showing the Teacher Details
        TeacherDetailsHeaderCard(
            selectedTeacher = selectedTeacher,
            onBackPressed = onBackClick
        )

        // Spacer of Height 16 dp
        Spacer(modifier = Modifier.height(16.dp))


        // Showing the Progress Bar
        Box(
            modifier = Modifier
                .padding(end = 16.dp, start = 16.dp)
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IndividualTeacherSuccess(
    loading: Boolean = false,
    reviewData: ReviewData,
    selectedTeacher: IndividualFacultyData,
    currentUserId: String?,
    onBackClick: () -> Unit = {},
    refreshReviews: () -> Unit = {}
) {
    // Lazy Column to Show the List of Reviews
    PullToRefresh(
        items = reviewData.individualReviewData!!,
        isRefreshing = loading,
        onRefresh = {
            refreshReviews()
        },
        preContent = {
            TeacherDetailsHeaderCard(
                selectedTeacher = selectedTeacher,
                onBackPressed = onBackClick
            )

            // Spacer of Height 16 dp
            Spacer(modifier = Modifier.height(16.dp))
        },
        content = {
            val reviewItem = it
            val rating = with(reviewItem.rating!!) {
                attendanceRating?.ratedPoints
                    ?.plus(teachingRating?.ratedPoints!!)
                    ?.plus(markingRating?.ratedPoints!!)
                    ?.plus(overallRating)?.div(4) ?: 0.0
            }
            ReviewCardItem(
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp),
                createdBy = reviewItem.createdBy,
                review = reviewItem.review!!,
                rating = rating,
                createdAt = reviewItem.createdAt!!,
                currentUserId = currentUserId
            )

            // Spacer of Height 16 dp
            Spacer(modifier = Modifier.height(16.dp))
        }
    )
//    LazyColumn {
//        items(reviewData.individualReviewData!!.size + 1) {
//            val itemCount = it - 1
//
//            // Drawing the Header of the Teacher with his overall stats
//            if (itemCount == -1) {
//                TeacherDetailsHeaderCard(
//                    selectedTeacher = selectedTeacher,
//                    onBackPressed = onBackClick
//                )
//
//                // Spacer of Height 16 dp
//                Spacer(modifier = Modifier.height(16.dp))
//            } else {
//                val reviewItem = reviewData.individualReviewData[itemCount]
//                val rating = with(reviewItem.rating!!) {
//                    attendanceRating?.ratedPoints
//                        ?.plus(teachingRating?.ratedPoints!!)
//                        ?.plus(markingRating?.ratedPoints!!)
//                        ?.plus(overallRating)?.div(4) ?: 0.0
//                }
//                ReviewCardItem(
//                    modifier = Modifier
//                        .padding(end = 16.dp, start = 16.dp),
//                    createdBy = reviewItem.createdBy,
//                    review = reviewItem.review!!,
//                    rating = rating,
//                    createdAt = reviewItem.createdAt!!,
//                    currentUserId = currentUserId
//                )
//
//                // Spacer of Height 16 dp
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//    }
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
    textToShow: String,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Drawing the Header of the Teacher with his overall stats
        TeacherDetailsHeaderCard(
            selectedTeacher = selectedTeacher,
            onBackPressed = onBackClick
        )

        // Spacer of Height 16 dp
        Spacer(modifier = Modifier.height(16.dp))

        // This is a text Button which says to Try Again
        TextButton(
            onClick = { onClickRetry() },
            modifier = Modifier.padding(end = 16.dp, start = 16.dp),
        ) {
            Text(
                text = textToShow,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}