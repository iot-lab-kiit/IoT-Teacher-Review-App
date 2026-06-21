package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
        facultyList.HandlePagingData(
            loadingBlock = { FacultyLoadingScreen() }
        ) { pagingData ->
            FacultyListSuccessScreen(
                faculties = pagingData,
                onFacultySelected = {
                    setEvent(FacultyEvent.FacultySelected(it))
                    navigator(FACULTY_DETAIL_ROUTE)
                },
                onClearClick = { setEvent(FacultyEvent.FetchFacultyList) },
                onSearchClick = { setEvent(FacultyEvent.FetchFacultyByName(it)) }
            )
        }
    }
}

@Composable
private fun FacultyLoadingScreen() {
    // Single scrolling LazyColumn with the search bar as the first item —
    // mirrors FacultyListSuccessScreen so the skeleton lines up exactly with
    // the loaded content and never overflows/overlaps the search bar or nav bar.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            SearchBar(
                label = "Search",
                placeholder = "Search a faculty...",
                onClearClick = { },
                onSearchClicked = { },
                onValueChange = { }
            )
        }
        items(8) { FacultySkeletonCard() }
    }
}

@Composable
fun FacultyListSuccessScreen(
    faculties: LazyPagingItems<RemoteFaculty>,
    onFacultySelected: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    // ✅ Get the single HazeState from AppScreen — same instance for ALL cards
    val hazeState = LocalHazeState.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            SearchBar(
                label = "Search",
                placeholder = "Search a faculty...",
                onClearClick = onClearClick,
                onSearchClicked = onSearchClick,
                onValueChange = {
                    if (it.length >= 3) onSearchClick(it)
                    else if (it.isEmpty()) onClearClick()
                }
            )
        }

        items(faculties.itemCount) {
            faculties[it]?.let { faculty ->
                // ✅ hazeState passed in — same object that has hazeSource on AppScreen bg
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