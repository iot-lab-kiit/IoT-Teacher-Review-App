package `in`.iot.lab.teacherreview.feature_authentication.data.models

/**
 * This is the structure of the User Data that the Database Server returns when asked to Login into
 * the App
 *
 * @property accessToken this is the Access Token which will be used by the user from now on to do
 * all the Other Calls inside the APP
 * @property user This is the UserData which contains the Details about the User and all
 */
data class UserAuthentication(
    val accessToken : String ,
    val user : UserData
)
