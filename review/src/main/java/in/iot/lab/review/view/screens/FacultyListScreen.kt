package `in`.iot.lab.review.view.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.SearchBar
import `in`.iot.lab.design.state.HandlePagingData
import `in`.iot.lab.review.view.components.FacultyDataUI
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.FACULTY_DETAIL_ROUTE
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty


@Composable
fun FacultyListScreenControl(
    facultyList: LazyPagingItems<RemoteFaculty>,
    setEvent: (FacultyEvent) -> Unit,
    navigator: (String) -> Unit
) {

    val context = LocalContext.current as Activity
    BackHandler { context.finish() }


    AppScreen {

        facultyList.HandlePagingData { pagingData ->
            FacultyListSuccessScreen(
                faculties = pagingData,
                onFacultySelected = {
                    setEvent(FacultyEvent.FacultySelected(it))
                    navigator(FACULTY_DETAIL_ROUTE)
                },
                onClearClick = {
                    setEvent(FacultyEvent.FetchFacultyList)
                },
                onSearchClick = {
                    setEvent(FacultyEvent.FetchFacultyByName(it))
                }
            )
        }
    }
}


@Composable
fun FacultyListSuccessScreen(
    faculties: LazyPagingItems<RemoteFaculty>,
    onFacultySelected: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        SearchBar(
            label = "Search",
            placeholder = "Search a faculty...",
            onClearClick = onClearClick,
            onSearchClicked = onSearchClick,
            onValueChange = {
                if (it.isNotEmpty() && it.length % 2 == 0)
                    onSearchClick(it)
            }
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            items(faculties.itemCount) {
                faculties[it]?.let { faculty ->
                    FacultyDataUI(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onFacultySelected(faculty.id) },
                        name = faculty.name ?: "Faculty Name",
                        photoUrl = faculty.photoUrl ?: "",
                        experience = faculty.experience,
                        avgRating = faculty.avgRating ?: 0.0,
                        totalRating = faculty.totalRating ?: 0
                    )
                }
            }

            // Spacer in the end
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}