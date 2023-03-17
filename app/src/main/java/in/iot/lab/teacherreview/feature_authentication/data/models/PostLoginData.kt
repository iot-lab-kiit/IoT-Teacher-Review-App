package `in`.iot.lab.teacherreview.feature_authentication.data.models

/**
 * This data class contains the Structure in which we can post a Login Request to the Server
 *
 * @property email contains the email of the user
 * @property password contains the password of the User
 * @property strategy contains the Strategy of the User Login
 */
data class PostLoginData(
    val email : String ,
    val password : String ,
    val strategy: String = "local"
)