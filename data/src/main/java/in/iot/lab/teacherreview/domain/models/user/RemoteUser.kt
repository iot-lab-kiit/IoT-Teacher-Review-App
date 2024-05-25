package `in`.iot.lab.teacherreview.domain.models.user

import com.google.firebase.auth.FirebaseUser
import com.google.gson.annotations.SerializedName


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
    @SerializedName("_id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("photoUrl")
    val photoUrl: String?,
    @SerializedName("role")
    val role: Int,
    @SerializedName("status")
    val status: Int
)


fun FirebaseUser.toRemoteUser(): RemoteUser {
    return RemoteUser(
        id = "",
        uid = uid,
        name = displayName,
        email = email,
        photoUrl = photoUrl?.toString(),
        role = 0,
        status = 0
    )
}