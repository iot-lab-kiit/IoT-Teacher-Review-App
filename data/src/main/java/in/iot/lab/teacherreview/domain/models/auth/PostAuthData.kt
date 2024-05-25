package `in`.iot.lab.teacherreview.domain.models.auth

import com.google.gson.annotations.SerializedName

data class PostAuthData(
    @SerializedName("token")
    val token: String
)