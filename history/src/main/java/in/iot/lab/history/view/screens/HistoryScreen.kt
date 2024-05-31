package `in`.iot.lab.history.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.animations.AmongUsAnimation
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ReviewDataUI
import `in`.iot.lab.history.view.event.HistoryEvent
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse


@Composable
fun HistoryScreenControl(
    historyList: LazyPagingItems<RemoteReviewHistoryResponse>,
    deleteState: UiState<Unit>,
    setEvent: (HistoryEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(HistoryEvent.FetchHistory)
    }

    AppScreen {

        // History Review Data UI
        HistorySuccessScreen(
            historyList = historyList,
            onDeletePress = { setEvent(HistoryEvent.RemoveReview(it)) }
        )

        when (deleteState) {
            is UiState.Loading -> {
                AmongUsAnimation()
            }

            is UiState.Success -> {
                setEvent(HistoryEvent.ResetRemoveState)
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = deleteState.message,
                    onCancel = {},
                    onTryAgain = { setEvent(HistoryEvent.FetchHistory) }
                )
            }

            else -> {
                // Do Nothing
            }
        }

        when {

            // Refresh
            historyList.loadState.refresh is LoadState.Loading -> {
                AmongUsAnimation()
            }

            // Append
            historyList.loadState.append is LoadState.Loading -> {
                AmongUsAnimation()
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
fun HistorySuccessScreen(
    historyList: LazyPagingItems<RemoteReviewHistoryResponse>,
    onDeletePress: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(historyList.itemCount) {

            historyList[it]?.let { history ->
                ReviewDataUI(
                    title = history.createdFor.name,
                    rating = history.rating,
                    description = history.feedback,
                    photoUrl = history.createdFor.photoUrl ?: "",
                    createdAt = history.createdAt,
                    onDeletePress = { onDeletePress(history.id) }
                )
            }
        }
    }
}