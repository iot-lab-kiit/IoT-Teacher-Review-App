package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.components.FacultyDataUI
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.FACULTY_DETAIL_ROUTE
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty


@Composable
fun FacultyListScreenControl(
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
                FacultyListSuccessScreen(
                    faculties = facultyListState.data,
                    onTeacherSelected = {
                        setEvent(FacultyEvent.FacultySelected(it))
                        navigator(FACULTY_DETAIL_ROUTE)
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
fun FacultyListSuccessScreen(
    faculties: List<RemoteFaculty>,
    onTeacherSelected: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(faculties.size) {

            val faculty = faculties[it]

            FacultyDataUI(
                modifier = Modifier.clickable { onTeacherSelected(faculty.id) },
                name = faculty.name,
                photoUrl = faculty.photoUrl ?: "",
                experience = "${faculty.experience ?: 0} years",
                avgRating = faculty.avgRating ?: 0.0,
                totalRating = faculty.totalRating ?: 0,
            )
        }
    }
}