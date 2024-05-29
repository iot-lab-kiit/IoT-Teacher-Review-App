package `in`.iot.lab.history.view.screens

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
import `in`.iot.lab.history.view.components.ReviewDataUI
import `in`.iot.lab.history.view.event.HistoryEvent
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse


@Composable
fun HistoryScreenControl(
    historyList: LazyPagingItems<RemoteReviewHistoryResponse>,
    setEvent: (HistoryEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(HistoryEvent.FetchHistory)
    }

    AppScreen {

        // History Review Data UI
        HistorySuccessScreen(historyList = historyList)

        when {

            // Refresh
            historyList.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator()
            }

            // Append
            historyList.loadState.append is LoadState.Loading -> {
                CircularProgressIndicator()
            }

            // Refresh error
            historyList.loadState.refresh is LoadState.Error -> {
                AppFailureScreen(
                    text = (historyList.loadState.refresh as LoadState.Error).error.message.toString(),
                    onCancel = {},
                    onTryAgain = historyList::refresh
                )
            }
        }
    }
}


@Composable
fun HistorySuccessScreen(historyList: LazyPagingItems<RemoteReviewHistoryResponse>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(historyList.itemCount) {

            historyList[it]?.let { history ->
                ReviewDataUI(
                    title = history.createdFor.name,
                    rating = history.rating,
                    description = history.feedback,
                    photoUrl = history.createdFor.photoUrl ?: ""
                )
            }
        }
    }
}