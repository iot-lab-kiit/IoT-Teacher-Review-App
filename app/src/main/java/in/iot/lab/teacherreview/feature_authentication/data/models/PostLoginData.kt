package `in`.iot.lab.teacherreview.feature_authentication.data.models

/**
 * This data class contains the Structure in which we can post a Login Request to the Server
 *
 * @property accessToken contains the accessToken of the User
 * @property strategy contains the Strategy of the User Login
 */
data class PostLoginData(
    val accessToken: String,
    val strategy: String = "firebase"
)