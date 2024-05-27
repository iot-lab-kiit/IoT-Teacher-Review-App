package `in`.iot.lab.history.view.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
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
                HistorySuccessScreen(
                    history = historyState.data,
                    setEvent = setEvent
                )
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
fun HistorySuccessScreen(
    history: List<RemoteReviewHistoryResponse>,
    setEvent: (HistoryEvent) -> Unit
) {

}