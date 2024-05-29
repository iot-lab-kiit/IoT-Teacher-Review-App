package `in`.iot.lab.teacherreview.domain.models.review

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser


/**
 * This data class is the blueprint for the Review History Response Schema.
 *
 * @param id This is the id provided from the Database.
 * @param createdFor This is the [RemoteFaculty] object for which the review is created.
 * @param rating This is the rating given by [RemoteUser].
 * @param feedback This is the feedback given by the [RemoteUser]
 * @param status This is the status of the review.
 * @param createdAt This is the date and time when the review is created.
 * @param updatedAt This is the date and time when the review is updated.
 * @param v This is the version of the review.
 */
data class RemoteReviewHistoryResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("createdFor")
    val createdFor: RemoteFaculty,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("feedback")
    val feedback: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)
