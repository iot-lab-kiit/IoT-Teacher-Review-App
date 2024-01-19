package `in`.iot.lab.teacherreview.feature_authentication.data.models

import com.google.firebase.auth.FirebaseUser


data class LoginResult(
    val data: User?,
    val errorMessage: String?
)

data class User(
    val username: String?,
    val uid: String,
    val email: String,
    val photoUrl: String?,
)

fun FirebaseUser?.toUser(): User? {
    return this?.run {
        User(
            uid = uid,
            email = email ?: "",
            photoUrl = photoUrl?.toString(),
            username = displayName
        )
    }
}