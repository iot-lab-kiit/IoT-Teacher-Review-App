package `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote

import com.google.gson.annotations.SerializedName

/**
 * This data class contains the Structure in which we can post a Login Request to the Server
 *
 * @property accessToken contains the accessToken of the User
 * @property strategy contains the Strategy of the User Login
 */
data class PostLoginData(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("strategy")
    val strategy: String = "firebase"
)