package `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.components.PullToRefresh
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.User
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Faculty
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Review
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.ReviewCardItem
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.TeacherDetailsHeaderCard
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.navigation.TeacherListRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.state_action.TeacherListAction

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IndividualTeacherControl(
    navController: NavController,
    selectedTeacher: Faculty,
    currentUserId: String?,
    lazyPagingItems: LazyPagingItems<Review>,
    action: (TeacherListAction) -> Unit,
) {
    val loading by remember {
        derivedStateOf {
            val state = lazyPagingItems.loadState
            when {
                (state.source.refresh is LoadState.Loading) -> true
                (state.refresh is LoadState.Loading) -> true
                else -> false
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(TeacherListRoutes.AddRatingRoute.route)
                },
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.RateReview,
                        contentDescription = stringResource(id = R.string.add),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(text = "Add Your Review")
                }
            }
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            IndividualTeacherContent(loading = loading,
                lazyPagingItems = lazyPagingItems,
                selectedTeacher = selectedTeacher,
                currentUserId = currentUserId,
                onBackClick = navController::popBackStack,
                refreshReviews = {
                    action(
                        TeacherListAction.GetIndividualTeacherReviews(
                            selectedTeacher.id!!
                        )
                    )
                },
                onDelete = {
                    action(
                        TeacherListAction.DeleteReview(
                            reviewId = it
                        )
                    )
                })
        }
    }
}


/**
 * Individual Teacher Main Content for Review Screen with Pull to Refresh and Lazy Column
 *
 * @param loading This is the Loading State of the Screen
 * @param lazyPagingItems This is the Lazy Paging Items for the Reviews
 * @param selectedTeacher This is the Selected Teacher Data
 * @param currentUserId This is the Current User Id
 * @param onBackClick This is the Function to Navigate Back
 * @param refreshReviews This is the Function to Refresh the Reviews
 *
 */
@Composable
fun IndividualTeacherContent(
    loading: Boolean = false,
    lazyPagingItems: LazyPagingItems<Review>,
    selectedTeacher: Faculty,
    currentUserId: String?,
    onBackClick: () -> Unit = {},
    refreshReviews: () -> Unit = {},
    onDelete: (String) -> Unit,
) {
    val state: LazyListState = rememberLazyListState()
    PullToRefresh(
        lazyListState = state,
        isRefreshing = loading,
        onRefresh = refreshReviews,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            TeacherDetailsHeaderCard(
                selectedTeacher = selectedTeacher, onBackPressed = onBackClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(count = lazyPagingItems.itemCount) { index ->
            lazyPagingItems.get(index = index)?.let { review ->
                val rating = review.rating

                ReviewCardItem(
                    modifier = Modifier.padding(end = 16.dp, start = 16.dp),
                    createdBy = review.createdBy ?: User(),
                    review = review.feedback ?: "",
                    rating = rating,
                    createdAt = review.createdAt,
                    currentUserId = currentUserId,
                    onDelete = { onDelete(review.id) },
                )

                // Spacer of Height 16 dp
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        when {
            lazyPagingItems.loadState.refresh is LoadState.Loading && lazyPagingItems.itemCount == 0 -> {
                item {
                    CircularProgressIndicator()
                }
            }

            lazyPagingItems.loadState.refresh is LoadState.Loading && lazyPagingItems.itemCount == 0 -> {
                item {
                    CircularProgressIndicator()
                }
            }

            lazyPagingItems.loadState.refresh is LoadState.Error -> {
                item {
                    FailedToLoad(
                        onClickRetry = {},
                        textToShow = (lazyPagingItems.loadState.refresh as LoadState.Error).error.message
                            ?: "Error"
                    )
                }
            }

            lazyPagingItems.loadState.append is LoadState.Loading -> {
                item {
                    CircularProgressIndicator()
                }
            }

            lazyPagingItems.loadState.append is LoadState.Error -> {
                item {
                    FailedToLoad(
                        onClickRetry = {},
                        textToShow = (lazyPagingItems.loadState.refresh as LoadState.Error).error.message
                            ?: "Error"
                    )
                }
            }

        }

        if (lazyPagingItems.loadState.append.endOfPaginationReached) {
            item {
                Text(
                    text = "No more reviews to show",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FailedToLoad(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
    textToShow: String,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { onClickRetry() },
            modifier = Modifier.padding(end = 16.dp, start = 16.dp),
        ) {
            Text(
                text = textToShow, style = MaterialTheme.typography.titleMedium
            )
        }
    }
}