package `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote


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