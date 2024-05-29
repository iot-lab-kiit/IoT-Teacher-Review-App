package `in`.iot.lab.teacherreview.domain.models.review

import com.google.gson.annotations.SerializedName


data class PostReviewBody(
    @SerializedName("createdBy")
    val createdBy: String?,
    @SerializedName("createdFor")
    val createdFor: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("feedback")
    val feedback: String?
)