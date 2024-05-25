package `in`.iot.lab.teacherreview.feature_teacherlist.data.remote

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("message")
    val message: String? = null,
)
