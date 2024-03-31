package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote

import com.google.gson.annotations.SerializedName

/**
 * This contains the structure of Rating Data
 *
 * @param overallRating This is the overall Rating
 * @param teachingRating This is the teaching Rating
 * @param markingRating This is the marking rating
 * @param attendanceRating This is the attendance Rating
 */
data class RatingData(
    var overallRating: Double = 0.0,
    @SerializedName("teaching")
    val teachingRating: RatingParameterData? = null,
    @SerializedName("marking")
    val markingRating: RatingParameterData? = null,
    @SerializedName("attendance")
    val attendanceRating: RatingParameterData? = null
)
