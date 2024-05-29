package `in`.iot.lab.history.view.event

sealed class HistoryEvent {

    data object FetchHistory : HistoryEvent()
}