package `in`.iot.lab.teacherreview.feature_authentication.domain.repository

import `in`.iot.lab.teacherreview.feature_authentication.domain.models.LocalUser

interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): Result<LocalUser>
    suspend fun logout()

    fun isUserSignedIn(): Boolean

    suspend fun getUserIdToken(): Result<String>
}