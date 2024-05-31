package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.border
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
import `in`.iot.lab.design.animations.AmongUsAnimation
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScaffold
import `in`.iot.lab.design.components.FAB
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.components.FacultyDataUI
import `in`.iot.lab.design.components.ReviewDataUI
import `in`.iot.lab.review.view.components.isScrollingUp
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.view.navigation.REVIEW_POST_ROUTE
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse


@Composable
fun ReviewDetailScreenControl(
    faculty: UiState<RemoteFacultyReviewResponse>,
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
        when (faculty) {

            is UiState.Idle -> {
                setEvent(FacultyEvent.GetFacultyDetails)
            }

            is UiState.Loading -> {
                AmongUsAnimation()
            }

            is UiState.Success -> {
                ReviewDetailSuccessScreen(
                    faculty = faculty.data,
                    lazyListState = lazyListState
                )
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
    faculty: RemoteFacultyReviewResponse,
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
                modifier = Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
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
                text = "· Reviews - ${faculty.reviewList?.size ?: 0}",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // Review List
        faculty.reviewList?.let { reviews ->
            items(reviews.size) {
                val review = reviews[it]
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