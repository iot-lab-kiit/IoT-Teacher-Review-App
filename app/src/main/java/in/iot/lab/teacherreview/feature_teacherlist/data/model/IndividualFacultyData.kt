package `in`.iot.lab.teacherreview.feature_teacherlist.data.model

/**
 * This is the structure of the Data of Each Individual Teacher fetched
 * in the teacher list Screen
 *
 * @param _id Auto Generated from the Server
 * @param name Name of the Teacher
 * @param avgTeachingRating Average teaching rating of the Teacher
 * @param avgMarkingRating Average Marking Rating of the Teacher
 * @param avgAttendanceRating Average Attendance Rating of the Teacher
 * @param code Teacher Code
 * @property avgRating Average Rating of the Teacher
 */
data class IndividualFacultyData(
    val _id: String,
    val name: String = "",
    val avgTeachingRating: Double = 0.0,
    val avgMarkingRating: Double = 0.0,
    val avgAttendanceRating: Double = 0.0,
    val code: String? = null,
    val avatar: String? = null
) {
    val avgRating: Double
        get() = (avgTeachingRating + avgMarkingRating + avgAttendanceRating) / 3

    // TODO: Add total reviews count serverside
    val totalReviews: Int
        get() = 100
}