package `in`.iot.lab.teacherreview.feature_authentication.data.models


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("authentication")
    val authentication: Authentication? = Authentication()
)
data class Authentication(
    @SerializedName("payload")
    val payload: Payload? = Payload()
)

data class Payload(
    @SerializedName("user")
    val user: User? = User(),
)