package `in`.iot.lab.teacherreview.feature_teacherlist.data.model

/**
 * This is the structure of the Data of Each Individual Teacher fetched
 * in the teacher list Screen
 *
 * @param _id Auto Generated from the Server
 * @param name Name of the Teacher
 * @param avgRating Average Rating of the Teacher
 * @param avgTeachingRating Average teaching rating of the Teacher
 * @param avgMarkingRating Average Marking Rating of the Teacher
 * @param avgAttendanceRating Average Attendance Rating of the Teacher
 * @param code Teacher Code
 */
data class IndividualFacultyData(
    val _id: String,
    val name: String = "",
    var avgRating: Double = 0.0,
    val avgTeachingRating: Double = 0.0,
    val avgMarkingRating: Double = 0.0,
    val avgAttendanceRating: Double = 0.0,
    val code: String? = null
)