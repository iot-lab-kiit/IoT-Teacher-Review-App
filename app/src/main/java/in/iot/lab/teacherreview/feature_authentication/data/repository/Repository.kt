package `in`.iot.lab.teacherreview.feature_authentication.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_authentication.data.models.LoginResult
import `in`.iot.lab.teacherreview.feature_authentication.data.models.toLocalUser
import kotlinx.coroutines.tasks.await

/**
 * This class basically is responsible for calling for the Data and returning the Data needed by
 * the app
 *
 * @property startLoginWithGoogle This function is used to start the Login with Google Process
 */
class Repository {
    private val auth = Firebase.auth

    fun checkIfUserIsLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    private fun getCurrentUser() = auth.currentUser

    fun getCurrentUserIdToken() =
        if (checkIfUserIsLoggedIn()) getCurrentUser()!!.getIdToken(false).result.token else null

    suspend fun startLoginWithGoogle(context: Context): LoginResult {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetSignInWithGoogleOption
                    .Builder(context.getString(R.string.web_client_id))
                    .build()
            )
            .build()

        val credentialManager = CredentialManager.create(context)

        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )
        return handleSignIn(result)
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): LoginResult {
        return when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        loginWithFirebase(idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        LoginResult(
                            data = null,
                            errorMessage = "Received an invalid google id token response"
                        )
                    }
                } else {
                    LoginResult(
                        data = null,
                        errorMessage = "Unexpected type of credential"
                    )
                }
            }

            else -> {
                LoginResult(
                    data = null,
                    errorMessage = "Unexpected type of credential"
                )
            }
        }
    }

    private suspend fun loginWithFirebase(idToken: String): LoginResult {
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)
        val firebaseUser = auth.signInWithCredential(googleCredentials).await().user
        return LoginResult(
            data = firebaseUser.toLocalUser(),
            errorMessage = null
        )
    }
}