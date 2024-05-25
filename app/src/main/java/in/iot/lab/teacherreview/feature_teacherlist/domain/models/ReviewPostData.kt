package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models

import com.google.gson.annotations.SerializedName

data class ReviewPostData(
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("createdFor")
    val createdFor: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("feedback")
    val feedback: String
)
