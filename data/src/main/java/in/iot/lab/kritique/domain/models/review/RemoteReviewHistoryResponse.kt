package `in`.iot.lab.kritique.domain.models.review

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.kritique.domain.models.user.RemoteUser


/**
 * This data class is the blueprint for the Review History Response Schema.
 *
 * @param id This is the id provided from the Database.
 * @param createdFor This is the [RemoteFaculty] object for which the review is created.
 * @param rating This is the rating given by [RemoteUser].
 * @param feedback This is the feedback given by the [RemoteUser]
 * @param createdAt This is the date and time when the review is created.
 */
data class RemoteReviewHistoryResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("createdFor")
    val createdFor: RemoteFaculty,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("feedback")
    val feedback: String,
    @SerializedName("createdAt")
    val createdAt: String
)
