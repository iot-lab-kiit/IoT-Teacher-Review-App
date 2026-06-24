package `in`.iot.lab.history.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import `in`.iot.lab.history.view.screens.HistoryScreenControl
import `in`.iot.lab.history.vm.HistoryViewModel
import `in`.iot.lab.kritique.domain.models.review.RemoteReviewHistoryResponse


const val HISTORY_ROUTE = "history-root-route"

fun NavGraphBuilder.historyNavGraph(
    onEditReview: (RemoteReviewHistoryResponse) -> Unit
) {

    composable(HISTORY_ROUTE) {

        val viewModel: HistoryViewModel = hiltViewModel()
        val historyList = viewModel.history.collectAsLazyPagingItems()
        val reviewDeleteState = viewModel.deleteReviewState.collectAsState().value

        HistoryScreenControl(
            historyList = historyList,
            deleteState = reviewDeleteState,
            setEvent = viewModel::uiListener,
            onEditReview = onEditReview
        )
    }
}