package `in`.iot.lab.teacherreview.feature_teacherlist.ui.action

sealed class HistoryActions{

    data object GetStudentReviewHistory : HistoryActions()
    data class DeleteReview(val reviewId: String) : HistoryActions()
}