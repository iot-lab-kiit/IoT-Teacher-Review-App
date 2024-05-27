package `in`.iot.lab.history.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.history.view.components.ReviewDataUI
import `in`.iot.lab.history.view.event.HistoryEvent
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse


@Composable
fun HistoryScreenControl(
    historyState: UiState<List<RemoteReviewHistoryResponse>>,
    setEvent: (HistoryEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(HistoryEvent.FetchHistory)
    }

    AppScreen {
        when (historyState) {

            is UiState.Idle -> {
                setEvent(HistoryEvent.FetchHistory)
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                HistorySuccessScreen(history = historyState.data)
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = historyState.message,
                    onCancel = { },
                    onTryAgain = { setEvent(HistoryEvent.FetchHistory) }
                )
            }
        }
    }
}


@Composable
fun HistorySuccessScreen(history: List<RemoteReviewHistoryResponse>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(history.size) {
            ReviewDataUI(
                title = history[it].createdFor.name,
                rating = history[it].rating,
                description = history[it].feedback,
                photoUrl = history[it].createdFor.photoUrl ?: ""
            )
        }
    }
}