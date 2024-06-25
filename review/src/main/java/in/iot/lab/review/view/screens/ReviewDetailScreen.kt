package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.components.AppScaffold
import `in`.iot.lab.design.components.FAB
import `in`.iot.lab.review.view.components.FacultyDataUI
import `in`.iot.lab.design.components.ReviewDataUI
import `in`.iot.lab.design.state.HandlePagingData
import `in`.iot.lab.design.state.HandleUiState
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.components.isScrollingUp
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.kritique.domain.models.review.RemoteFacultyReview


@Composable
fun ReviewDetailScreenControl(
    facultyData: UiState<RemoteFaculty>,
    reviewList: LazyPagingItems<RemoteFacultyReview>,
    onFabClick: () -> Unit,
    onBackClick: () -> Unit,
    setEvent: (FacultyEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(FacultyEvent.GetFacultyDetails)
    }

    val lazyListState = rememberLazyListState()

    AppScaffold(
        floatingActionButton = {
            FAB(
                text = "Review",
                onClick = onFabClick,
                extended = lazyListState.isScrollingUp()
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets)
    ) {

        facultyData.HandleUiState(
            onCancel = onBackClick,
            onTryAgain = {
                setEvent(FacultyEvent.GetFacultyDetails)
            }
        ) { faculty ->

            reviewList.HandlePagingData {
                ReviewDetailSuccessScreen(
                    faculty = faculty,
                    reviewList = reviewList,
                    lazyListState = lazyListState
                )
            }
        }
    }
}


@Composable
fun ReviewDetailSuccessScreen(
    faculty: RemoteFaculty,
    reviewList: LazyPagingItems<RemoteFacultyReview>,
    lazyListState: LazyListState
) {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // User Profile Data
        item {
            FacultyDataUI(
                name = faculty.name,
                photoUrl = faculty.photoUrl ?: "",
                experience = faculty.experience ?: 0.0,
                avgRating = faculty.avgRating ?: 0.0,
                totalRating = faculty.totalRating ?: 0
            )
        }

        // Review Text with count
        faculty.totalRating?.let {
            if (it != 0) {
                item {
                    Text(
                        text = "· Reviews - $it",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        // Review List
        items(reviewList.itemCount) {
            reviewList[it]?.let { review ->
                ReviewDataUI(
                    title = review.createdBy?.name ?: "Reviewer Name",
                    rating = review.rating ?: 0.0,
                    description = review.feedback ?: "Alas! The reviewer gave no feedback ",
                    photoUrl = review.createdBy?.photoUrl ?: "",
                    createdAt = review.createdAt ?: ""
                )
            }
        }
    }
}