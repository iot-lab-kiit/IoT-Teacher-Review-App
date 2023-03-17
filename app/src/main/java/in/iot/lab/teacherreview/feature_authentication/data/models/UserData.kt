package `in`.iot.lab.teacherreview.feature_authentication.data.models

/**
 * This is the Structure of the User data which will be sent by the Database server and be stored
 * inside the Local Database
 *
 * @property _id this is the unique ID for every user
 * @property email this is the email of the user from which the user logged in
 * @property name this contains the name of the User
 * @property role this contains the information whether the user is an admin or normal user
 * @property status this contains whether the user is a verified User
 */
data class UserData(
    val _id : String = "" ,
    val email : String = "" ,
    val name : String = "",
    val role : Int = 0,
    val status : Int = 0
)