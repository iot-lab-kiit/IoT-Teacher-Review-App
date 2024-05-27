package `in`.iot.lab.history.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.history.view.screens.HistoryScreenControl
import `in`.iot.lab.history.vm.HistoryViewModel


const val HISTORY_ROUTE = "history-root-route"

fun NavGraphBuilder.historyNavGraph() {

    composable(HISTORY_ROUTE) {

        val viewModel: HistoryViewModel = hiltViewModel()
        val historyState = viewModel.historyState.collectAsState().value

        HistoryScreenControl(
            historyState = historyState,
            setEvent = viewModel::uiListener
        )
    }
}