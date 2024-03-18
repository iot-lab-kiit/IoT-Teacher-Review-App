package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state_action

data class ReviewStateFlow(
    var markingRating: Double = 1.0,
    var attendanceRating: Double = 1.0,
    var teachingRating: Double = 1.0,
    var overallReview: String = "",
    var markingReview: String = "",
    var attendanceReview: String = "",
    var teachingReview: String = ""
)
