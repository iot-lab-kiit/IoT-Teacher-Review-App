package `in`.iot.lab.teacherreview.feature_authentication.data.models

/**
 * This Data model contains the structure in which the post Request is sent to the Server
 *
 * @param name This contains the name of the User
 * @param email This contains the email of the User
 * @param password This contains the password of the user
 */
data class PostSignupData(
    val name: String,
    val email: String,
    val password: String
)