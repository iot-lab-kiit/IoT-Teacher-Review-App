package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote

import com.google.gson.annotations.SerializedName

/**
 * This is the structure of the Review Data which will be given in response to the API call
 *
 * @param avgAttendanceRating This contains the average teaching rating
 * @param avgMarkingRating This contains the average marking rating
 * @param avgAttendanceRating This contains the average attendance rating
 * @param total This contains the total ratings
 * @param limit This contains the limit unto which the ratings will be fetched
 * @param skip This contains the amount of data from list ot be skipped from the database
 * @param individualReviewData This contains individual Review Data
 */
data class ReviewData(
    var avgTeachingRating: Double = 0.0,
    var avgMarkingRating: Double = 0.0,
    var avgAttendanceRating: Double = 0.0,
    val total: Int = 0,
    val limit: Int = 0,
    val skip: Int = 0,
    @SerializedName("data")
    val individualReviewData: List<IndividualReviewData>? = null
)

