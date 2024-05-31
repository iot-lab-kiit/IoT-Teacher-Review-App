package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.animations.AmongUsAnimation
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScaffold
import `in`.iot.lab.design.components.FAB
import `in`.iot.lab.review.view.components.FacultyDataUI
import `in`.iot.lab.design.components.ReviewDataUI
import `in`.iot.lab.review.view.components.isScrollingUp
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.REVIEW_POST_ROUTE
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReview


@Composable
fun ReviewDetailScreenControl(
    faculty: RemoteFaculty,
    reviewList: LazyPagingItems<RemoteFacultyReview>,
    navigator: (String) -> Unit,
    setEvent: (FacultyEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(FacultyEvent.GetFacultyDetails)
    }

    val lazyListState = rememberLazyListState()

    AppScaffold(
        floatingActionButton = {
            FAB(
                onClick = { navigator(REVIEW_POST_ROUTE) },
                extended = lazyListState.isScrollingUp()
            )
        }
    ) {

        ReviewDetailSuccessScreen(
            faculty = faculty,
            reviewList = reviewList,
            lazyListState = lazyListState
        )

        when {


            // Refresh
            reviewList.loadState.refresh is LoadState.Loading -> {
                AmongUsAnimation()
            }


            // Append
            reviewList.loadState.append is LoadState.Loading -> {
                AmongUsAnimation()
            }

            // Refresh error
            reviewList.loadState.refresh is LoadState.Error -> {
                AppFailureScreen(
                    text = (reviewList.loadState.refresh as LoadState.Error).error.message.toString(),
                    onCancel = {},
                    onTryAgain = reviewList::refresh
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
        item {
            Text(
                text = "· Reviews - ${faculty.totalRating ?: 0}",
                style = MaterialTheme.typography.titleLarge
            )
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