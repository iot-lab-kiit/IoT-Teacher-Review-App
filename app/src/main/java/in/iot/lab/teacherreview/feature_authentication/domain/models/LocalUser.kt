package `in`.iot.lab.teacherreview.feature_authentication.domain.models

import com.google.firebase.auth.FirebaseUser


data class LocalUser(
    val username: String?,
    val uid: String,
    val email: String,
    val photoUrl: String?,
)

fun FirebaseUser.toLocalUser(): LocalUser {
    return LocalUser(
        uid = uid,
        email = email ?: "",
        photoUrl = photoUrl?.toString(),
        username = displayName
    )
}