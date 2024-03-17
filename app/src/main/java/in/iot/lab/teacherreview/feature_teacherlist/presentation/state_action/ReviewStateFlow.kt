package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state_action

data class ReviewStateFlow(
    var markingRating: Double,
    var attendanceRating: Double,
    var teachingRating: Double,
    var overallReview: String,
    var markingReview: String,
    var attendanceReview: String,
    var teachingReview: String
)
