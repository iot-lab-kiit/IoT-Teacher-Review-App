package `in`.iot.lab.history.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.animations.DeleteAnimation
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.ReviewDataUI
import `in`.iot.lab.design.state.HandlePagingData
import `in`.iot.lab.design.state.HandleUiState
import `in`.iot.lab.history.view.component.CustomDeleteDialog
import `in`.iot.lab.history.view.event.HistoryEvent
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.kritique.domain.models.review.RemoteReviewHistoryResponse


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

        deleteState.HandleUiState(
            onCancel = {
                // Nothing Particular needs to be done.
            },
            onTryAgain = { setEvent(HistoryEvent.FetchHistory) }
        ) {
            DeleteAnimation {
                setEvent(HistoryEvent.ResetRemoveState)
            }
        }

        historyList.HandlePagingData { pagingData ->

            // History Review Data UI
            HistorySuccessScreen(
                historyList = pagingData,
                onDeletePress = { setEvent(HistoryEvent.RemoveReview(it)) }
            )
        }
    }
}


@Composable
fun HistorySuccessScreen(
    historyList: LazyPagingItems<RemoteReviewHistoryResponse>,
    onDeletePress: (String) -> Unit
) {

    var deletePress by remember { mutableStateOf(false) }
    var deleteId by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
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
                    onDeletePress = {
                        deleteId = history.id
                        deletePress = true
                    }
                )
            }
        }
    }

    CustomDeleteDialog(
        deletePress = deletePress,
        onDismiss = { deletePress = false },
        onConfirm = {
            deletePress = false
            onDeletePress(deleteId)
        }
    )
}