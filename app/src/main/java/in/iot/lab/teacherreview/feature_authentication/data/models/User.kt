package `in`.iot.lab.teacherreview.feature_authentication.data.models

/**
 * This is the Structure of the User data which will be sent by the Database server and be stored
 * inside the Local Database
 *
 * @property _id this is the unique ID for every user
 * @property name this contains the name of the User
 * @property email this is the email of the user from which the user logged in
 * @property uid this is the unique ID of the user from the Authentication Server
 * @property pictureUrl this contains the URL of the Profile Picture of the User
 * @property role this contains the information whether the user is an admin or normal user
 * @property status this contains whether the user is a verified User
 */
data class User(
    val _id: String? = "",
    val name: String? = "",
    val email: String? = "",
    val uid: String? = "",
    val pictureUrl: String? = "",
    val role: Int? = 0,
    val status: Int? =0
)