package `in`.iot.lab.teacherreview.feature_authentication.data.models

import com.google.firebase.auth.FirebaseUser


data class LoginResult(
    val data: LocalUser?,
    val errorMessage: String?
)

data class LocalUser(
    val username: String?,
    val uid: String,
    val email: String,
    val photoUrl: String?,
)

fun FirebaseUser?.toLocalUser(): LocalUser? {
    return this?.run {
        LocalUser(
            uid = uid,
            email = email ?: "",
            photoUrl = photoUrl?.toString(),
            username = displayName
        )
    }
}