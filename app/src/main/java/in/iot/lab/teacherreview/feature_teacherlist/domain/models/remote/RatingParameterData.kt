package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote

import com.google.gson.annotations.SerializedName

/**
 * This is the structure of the meta-data of each Rating Data
 *
 * @param ratedPoints This is the Rated Points of the rating
 * @param description This is the description given by the user
 */
data class RatingParameterData(
    @SerializedName("points")
    val ratedPoints: Double? = null,
    val description: String? = null
)