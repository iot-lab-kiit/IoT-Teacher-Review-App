package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.review.view.components.FacultyDataUI
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.FACULTY_DETAIL_ROUTE
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty


@Composable
fun FacultyListScreenControl(
    facultyList: LazyPagingItems<RemoteFaculty>,
    setEvent: (FacultyEvent) -> Unit,
    navigator: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(FacultyEvent.FetchFacultyList)
    }

    AppScreen {

        FacultyListSuccessScreen(
            faculties = facultyList,
            onTeacherSelected = {
                setEvent(FacultyEvent.FacultySelected(it))
                navigator(FACULTY_DETAIL_ROUTE)
            }
        )


        when {

            // Refresh
            facultyList.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator()
            }

            // Append
            facultyList.loadState.append is LoadState.Loading -> {
                CircularProgressIndicator()
            }

            // Refresh error
            facultyList.loadState.refresh is LoadState.Error -> {
                AppFailureScreen(
                    text = (facultyList.loadState.refresh as LoadState.Error).error.message.toString(),
                    onCancel = {},
                    onTryAgain = facultyList::refresh
                )
            }
        }
    }
}


@Composable
fun FacultyListSuccessScreen(
    faculties: LazyPagingItems<RemoteFaculty>,
    onTeacherSelected: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(faculties.itemCount) {
            faculties[it]?.let { faculty ->
                FacultyDataUI(
                    modifier = Modifier.clickable { onTeacherSelected(faculty.id) },
                    name = faculty.name,
                    photoUrl = faculty.photoUrl ?: "",
                    experience = faculty.experience ?: 0.0,
                    avgRating = faculty.avgRating ?: 0.0,
                    totalRating = faculty.totalRating ?: 0,
                )
            }
        }
    }
}