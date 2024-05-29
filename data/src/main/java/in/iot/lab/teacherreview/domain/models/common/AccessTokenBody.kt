package `in`.iot.lab.teacherreview.domain.models.common

import com.google.gson.annotations.SerializedName

data class AccessTokenBody(
    @SerializedName("token")
    val token: String
)