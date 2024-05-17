package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote

import com.google.gson.annotations.SerializedName
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.User

data class Review(
    @SerializedName("_id")
    val id: String = "",
    @SerializedName("createdBy")
    val createdBy: User? = null,
    @SerializedName("createdFor")
    val createdFor: Faculty? = null,
    @SerializedName("rating")
    val rating: Double = 0.0,
    @SerializedName("feedback")
    val feedback: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("updatedAt")
    val updatedAt: String = "",
    @SerializedName("__v")
    val v: Int? = null
)