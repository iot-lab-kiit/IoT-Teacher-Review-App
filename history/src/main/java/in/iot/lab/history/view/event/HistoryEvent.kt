package `in`.iot.lab.history.view.event

sealed class HistoryEvent {

    data object FetchHistory : HistoryEvent()
    data class RemoveReview(val reviewId: String) : HistoryEvent()
    data object ResetRemoveState : HistoryEvent()
}