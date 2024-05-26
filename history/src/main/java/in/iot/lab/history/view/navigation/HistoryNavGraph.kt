package `in`.iot.lab.history.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.history.view.screens.HistoryScreenControl


const val HISTORY_ROUTE = "history-root-route"

fun NavGraphBuilder.historyNavGraph() {

    composable(HISTORY_ROUTE) {
        HistoryScreenControl()
    }
}