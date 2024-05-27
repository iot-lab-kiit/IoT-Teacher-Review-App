package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.TEACHER_DETAIL_ROUTE
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty


@Composable
fun ReviewScreenControl(
    facultyListState: UiState<List<RemoteFaculty>>,
    setEvent: (FacultyEvent) -> Unit,
    navigator: (String) -> Unit
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
                    onTeacherSelected = {
                        setEvent(FacultyEvent.FacultySelected(it))
                        navigator(TEACHER_DETAIL_ROUTE)
                    }
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
    onTeacherSelected: (String) -> Unit
) {

    LazyColumn {

        items(faculties.size) {
            Text(
                modifier = Modifier
                    .clickable {
                        onTeacherSelected(faculties[it].id)
                    }
                    .padding(16.dp),
                text = faculties[it].name
            )
        }
    }
}