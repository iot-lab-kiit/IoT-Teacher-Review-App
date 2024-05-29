package `in`.iot.lab.history.view.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import `in`.iot.lab.history.view.screens.HistoryScreenControl
import `in`.iot.lab.history.vm.HistoryViewModel


const val HISTORY_ROUTE = "history-root-route"

fun NavGraphBuilder.historyNavGraph() {

    composable(HISTORY_ROUTE) {

        val viewModel: HistoryViewModel = hiltViewModel()
        val historyList = viewModel.history.collectAsLazyPagingItems()

        HistoryScreenControl(
            historyList = historyList,
            setEvent = viewModel::uiListener
        )
    }
}