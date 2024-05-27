package `in`.iot.lab.review.view.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty


@Composable
fun ReviewScreenControl(
    facultyListState: UiState<List<RemoteFaculty>>,
    setEvent: (FacultyEvent) -> Unit
) {
    AppScreen {

        when (facultyListState) {

            is UiState.Idle -> {
                setEvent(FacultyEvent.FetchFacultyList)
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                ReviewSuccessScreen(
                    faculties = facultyListState.data,
                    setEvent = setEvent
                )
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = facultyListState.message,
                    onCancel = {

                    },
                    onTryAgain = {
                        setEvent(FacultyEvent.FetchFacultyList)
                    }
                )
            }
        }
    }
}


@Composable
fun ReviewSuccessScreen(
    faculties: List<RemoteFaculty>,
    setEvent: (FacultyEvent) -> Unit
) {

}