package `in`.iot.lab.teacherreview.feature_authentication.data.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.utils.await
import `in`.iot.lab.teacherreview.feature_authentication.data.models.LoginResult
import `in`.iot.lab.teacherreview.feature_authentication.data.models.toLocalUser

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

    fun getCurrentUser() = auth.currentUser

    suspend fun getCurrentUserIdToken(): String? {
        if (!checkIfUserIsLoggedIn()) return null

        val tokenTask = getCurrentUser()!!.getIdToken(false)
        tokenTask.await()
        return tokenTask.result?.token
    }

    fun startLoginWithGoogle(context: Context): Intent {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, options)
        return googleSignInClient.signInIntent
    }

    suspend fun handleSignIn(result: ActivityResult): LoginResult {
        return if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val idToken = account.idToken!!
                loginWithFirebase(idToken)
            } catch (e: Exception) {
                LoginResult(
                    data = null,
                    errorMessage = "Received an invalid google id token response"
                )
            }
        } else {
            LoginResult(
                data = null,
                errorMessage = "Google sign in failed"
            )
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

    fun logout(context: Context) {
        auth.signOut()
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, options)
        googleSignInClient.signOut()
    }
}