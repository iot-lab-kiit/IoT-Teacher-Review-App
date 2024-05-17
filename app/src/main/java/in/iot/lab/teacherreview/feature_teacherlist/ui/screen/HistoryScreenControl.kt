package `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.components.PullToRefresh
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.User
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Review
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.action.HistoryActions
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.ReviewCardItem

/*
 This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        HistoryScreenLoading()
    }
}

// This is the Preview function of the Screen when Success
@Preview("Light")
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewSuccess() {
    CustomAppTheme {
        HistoryScreenContent(
            ReviewData(),
            currentUserId = "1"
        )
    }
}

// This is the Preview function of the Screen when Failure
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewFailure() {
    CustomAppTheme {
        HistoryScreenFailure(
            onRetryClick = {}
        )
    }
}
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScreenControl(
    modifier: Modifier = Modifier,
    historyActions: (HistoryActions) -> Unit,
    lazyPagingItems: LazyPagingItems<Review>,
    currentUserId: String?
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

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
        ) {
            HistoryScreenContent(
                loading = loading,
                lazyPagingItems = lazyPagingItems,
                currentUserId = currentUserId,
                refreshHistory = {
                    historyActions(HistoryActions.GetStudentReviewHistory)
                },
                onDelete = { historyActions(HistoryActions.DeleteReview(it)) }
            )
        }
    }
}

@Composable
fun HistoryScreenContent(
    loading: Boolean = false,
    lazyPagingItems: LazyPagingItems<Review>,
    currentUserId: String?,
    refreshHistory: () -> Unit = {},
    onDelete: (String) -> Unit
) {
    val state: LazyListState = rememberLazyListState()
    PullToRefresh(
        lazyListState = state,
        isRefreshing = loading,
        onRefresh = refreshHistory,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(count = lazyPagingItems.itemCount) { index ->
            lazyPagingItems.get(index = index)?.let { review ->
                val rating = review.rating

                ReviewCardItem(
                    modifier = Modifier
                        .padding(end = 16.dp, start = 16.dp),
                    createdBy = review.createdBy ?: User(),
                    review = review.feedback ?: "",
                    rating = rating,
                    createdAt = review.createdAt,
                    currentUserId = currentUserId,
                    onDelete = { onDelete(review.id) }
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
                    HistoryScreenFailure(
                        onRetryClick = refreshHistory
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
                    HistoryScreenFailure(
                        onRetryClick = refreshHistory
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
fun HistoryScreenFailure(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onRetryClick
        ) {
            Text(
                text = stringResource(R.string.failed_to_load_tap_to_retry),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}