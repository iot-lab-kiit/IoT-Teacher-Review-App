package `in`.iot.lab.teacherreview.domain.models.review

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser


/**
 * This class is the blueprint for the Review data which will be given for the user.
 *
 * @param id This is the id provided from the Database.
 * @param createdBy This is the [RemoteUser] object which created this review.
 * @param rating This is the rating given by [RemoteUser].
 * @param feedback This is the feedback given by the [RemoteUser]
 */
data class RemoteFacultyReview(
    @SerializedName("_id")
    val id: String,
    @SerializedName("createdBy")
    val createdBy: RemoteUser?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("feedback")
    val feedback: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)