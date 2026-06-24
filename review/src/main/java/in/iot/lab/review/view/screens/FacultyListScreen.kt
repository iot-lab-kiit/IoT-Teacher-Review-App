package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.animations.FacultySkeletonCard
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.LocalHazeState
import `in`.iot.lab.design.components.SearchBar
import `in`.iot.lab.design.state.HandlePagingData
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.review.view.components.FacultyDataUI
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.FACULTY_DETAIL_ROUTE

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            SearchBar(
                label = "Search",
                placeholder = "Search a faculty...",
                onClearClick = { setEvent(FacultyEvent.FetchFacultyList) },
                onSearchClicked = { setEvent(FacultyEvent.FetchFacultyByName(it)) },
                onValueChange = {
                    if (it.length >= 3) setEvent(FacultyEvent.FetchFacultyByName(it))
                    else if (it.isEmpty()) setEvent(FacultyEvent.FetchFacultyList)
                }
            )

            // Only this area swaps between skeleton and the loaded list.
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                facultyList.HandlePagingData(
                    loadingBlock = { FacultySkeletonList() }
                ) { pagingData ->
                    FacultyList(
                        faculties = pagingData,
                        onFacultySelected = {
                            setEvent(FacultyEvent.FacultySelected(it))
                            navigator(FACULTY_DETAIL_ROUTE)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FacultySkeletonList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        items(8) { FacultySkeletonCard() }
    }
}

@Composable
private fun FacultyList(
    faculties: LazyPagingItems<RemoteFaculty>,
    onFacultySelected: (String) -> Unit
) {
    // Single HazeState from AppScreen — same instance for ALL cards.
    val hazeState = LocalHazeState.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        items(faculties.itemCount) {
            faculties[it]?.let { faculty ->
                hazeState?.let { state ->
                    FacultyDataUI(
                        modifier = Modifier.clickable { onFacultySelected(faculty.id) },
                        name = faculty.name,
                        photoUrl = faculty.photoUrl ?: "",
                        experience = faculty.experience,
                        avgRating = faculty.avgRating ?: 0.0,
                        totalRating = faculty.totalRating ?: 0,
                        hazeState = state
                    )
                }
            }
        }
    }
}
