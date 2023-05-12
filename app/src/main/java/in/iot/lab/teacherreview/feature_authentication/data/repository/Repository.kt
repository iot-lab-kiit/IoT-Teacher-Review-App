package `in`.iot.lab.teacherreview.feature_authentication.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_authentication.data.data_source.remote.RetrofitInstance.apiInstance
import `in`.iot.lab.teacherreview.feature_authentication.data.models.LoginResult
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostSignupData
import `in`.iot.lab.teacherreview.feature_authentication.data.models.User
import `in`.iot.lab.teacherreview.feature_authentication.util.LoginState
import `in`.iot.lab.teacherreview.feature_authentication.util.RegistrationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

/**
 * This class basically is responsible for calling for the Data and returning the Data needed by
 * the app
 *
 * @property postLoginRequest This calls the API and posts the User Authentication request
 * @property postSignupRequest This calls the API and posts the new User Request to the Server
 */
class Repository {
    private val auth = Firebase.auth

    // This calls the API and posts the User Authentication request
    suspend fun postLoginRequest(postLoginData: PostLoginData): LoginState {

        // Login Response from the Server
        val response = apiInstance.postLoginRequest(postLoginData)

        return if (response.isSuccessful) {

            // Setting the Current AccessToken received from The server for using it later
            Constants.setAccessToken(response.body()!!.accessToken)
            LoginState.Success(LoginResult(data = null, errorMessage = "Not Implemented"))
        } else
            LoginState.Failure(errorMessage = "Wrong Credentials or try again")
    }

    // This calls the API and posts the new User Request to the Server
    suspend fun postSignupRequest(postSignupData: PostSignupData): RegistrationState {

        // Signup Request Response From the Server
        val response = apiInstance.postSignupRequest(postSignupData)

        return if (response.isSuccessful)
            RegistrationState.Success(response.body()!!)
        else
            RegistrationState.Failure(errorMessage = "Please Check the Data entered")
    }

    suspend fun startLoginWithGoogle(context: Context): Flow<LoginState> = flow {
        try {
            emit(LoginState.Loading)
            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(
                    GetSignInWithGoogleOption
                        .Builder("926563265864-ub1ee0j8o9gqs6egssvcp92f332hecpb.apps.googleusercontent.com")
                        .build()
                )
                .build()

            val credentialManager = CredentialManager.create(context)

            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            emit(LoginState.Success(data = handleSignIn(result)))
        } catch (e: Exception) {
            emit(
                LoginState.Failure(
                    errorMessage = e.message ?: e.localizedMessage ?: "Unknown error"
                )
            )
        }
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
        val user = auth.signInWithCredential(googleCredentials).await().user
        return LoginResult(
            data = user?.run {
                User(
                    uid = uid,
                    email = email ?: "",
                    photoUrl = photoUrl?.toString(),
                    username = displayName
                )
            },
            errorMessage = null
        )
    }
}