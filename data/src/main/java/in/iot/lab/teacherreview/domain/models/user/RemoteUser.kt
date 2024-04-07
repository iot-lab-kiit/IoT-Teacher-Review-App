package `in`.iot.lab.teacherreview.domain.models.user

import com.google.firebase.auth.FirebaseUser


/**
 * This data class is the blueprint for the User Schema.
 *
 * @param id This is the id of the user from the Database.
 * @param uid This is the uid from the Firebase.
 * @param name This is the name of the user.
 * @param email This is the email of the user.
 * @param photoUrl This is the profile Photo Url String.
 * @param role This is the Role of the User which can be either { User , Admin }
 * @param status This is the status of the User which can be either { Approved , Not Approved }
 */
data class RemoteUser(
    val id: String,
    val uid: String,
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    val role: Int,
    val status: Int
)


fun FirebaseUser.toRemoteUser(): RemoteUser {
    return RemoteUser(
        id = "Dummy Data",
        uid = uid,
        name = displayName,
        email = email,
        photoUrl = photoUrl?.toString(),
        role = 0,
        status = 0
    )
}