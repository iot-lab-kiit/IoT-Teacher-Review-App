package `in`.iot.lab.teacherreview.feature_authentication.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import `in`.iot.lab.teacherreview.core.data.local.UserPreferences
import `in`.iot.lab.teacherreview.core.utils.await
import `in`.iot.lab.teacherreview.feature_authentication.data.remote.AuthApi
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.LocalUser
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.toLocalUser
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val firebaseAuth: FirebaseAuth,
    private val signInClient: GoogleSignInClient,
    private val userPreferences: UserPreferences
) : AuthRepository {
    override suspend fun loginWithGoogle(idToken: String): Result<LocalUser> {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()

            if (result.user == null) {
                return Result.failure(Exception("Google Login Failed"))
            }

            val user = result.user!!
            // Send the token to the backend server
            val postLoginData = PostLoginData(getUserIdToken().getOrThrow())
            // This will automatically throw an exception if the request fails so we don't need to check its value
            postLoginRequest(postLoginData).getOrThrow()
            return Result.success(user.toLocalUser())
        } catch (e: FirebaseAuthInvalidUserException) {
            return Result.failure(Exception("User does not exist"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return Result.failure(Exception("Invalid Google credentials"))
        } catch (e: FirebaseException) {
            return Result.failure(Exception(e.localizedMessage ?: "Google Login Failed"))
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
        signInClient.signOut()
        userPreferences.clear()
    }

    override fun isUserSignedIn(): Boolean = firebaseAuth.currentUser != null

    override suspend fun getUserIdToken(): Result<String> {
        try {
            if (!isUserSignedIn()) {
                return Result.failure(Exception("User is not signed in"))
            }

            return when (val token = firebaseAuth.currentUser?.getIdToken(false)?.await()?.token) {
                null -> Result.failure(Exception("Token is null"))
                else -> Result.success(token)
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun postLoginRequest(postLoginData: PostLoginData): Result<Boolean> {
        try {
            val response = authApi.postLoginRequest(postLoginData)
            if (!response.isSuccessful) {
                throw Exception("Failed to post login data")
            }

            val userId = response.body()?.authentication?.payload?.user?._id
            if (userId == null) {
                throw Exception("User id is null")
            }
            userPreferences.saveUserId(userId)
            return Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<LocalUser> {
        try {
            val firebaseUser = firebaseAuth.currentUser ?: return Result.failure(Exception("User is null"))
            return Result.success(firebaseUser.toLocalUser())
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}