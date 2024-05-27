package `in`.iot.lab.review.view.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.FAB
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.REVIEW_POST_ROUTE
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse


@Composable
fun ReviewDetailScreenControl(
    faculty: UiState<RemoteFacultyReviewResponse>,
    navigator: (String) -> Unit,
    setEvent: (FacultyEvent) -> Unit
) {
    AppScreen(
        floatingActionButton = {
            FAB(onClick = { navigator(REVIEW_POST_ROUTE) })
        }
    ) {
        when (faculty) {

            is UiState.Idle -> {
                setEvent(FacultyEvent.GetFacultyDetails)
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                ReviewDetailSuccessScreen(faculty.data)
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = faculty.message,
                    onCancel = {

                    },
                    onTryAgain = {
                        setEvent(FacultyEvent.GetFacultyDetails)
                    }
                )
            }
        }
    }
}


@Composable
fun ReviewDetailSuccessScreen(
    faculty: RemoteFacultyReviewResponse
) {

}