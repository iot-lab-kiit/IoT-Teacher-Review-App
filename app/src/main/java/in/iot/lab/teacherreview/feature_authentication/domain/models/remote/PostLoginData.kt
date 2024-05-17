package `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote

import com.google.gson.annotations.SerializedName

data class PostLoginData(
    @SerializedName("token")
    val token: String
)