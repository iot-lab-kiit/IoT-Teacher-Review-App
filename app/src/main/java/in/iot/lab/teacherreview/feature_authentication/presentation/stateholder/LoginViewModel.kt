package `in`.iot.lab.teacherreview.feature_authentication.presentation.stateholder

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.core.utils.UserUtils
import `in`.iot.lab.teacherreview.feature_authentication.data.data_source.remote.RetrofitInstance
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.LoginScreen
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.SignInLauncher
import `in`.iot.lab.teacherreview.feature_authentication.util.LoginState
import kotlinx.coroutines.launch

/**
 * This is the Login Screen [LoginScreen]'s State holder or viewModel Class which feeds Data
 * and state to the [LoginScreen] UI layer
 *
 * @property myRepository Repository Variable
 * @property loginState keeps a track of the Current State of the Login API Request
 */

class LoginViewModel : ViewModel() {
    private val api = RetrofitInstance.apiInstance
    private val myRepository = Repository()

    var loginState: LoginState by mutableStateOf(LoginState.Initialized)
        private set

    fun checkIfUserIsLoggedIn(): Boolean {
        return myRepository.checkIfUserIsLoggedIn()
    }

    fun signIn(context: Context, launcher: SignInLauncher) {
        viewModelScope.launch {
            try {
                // Setting the LoginState to Loading
                loginState = LoginState.Loading
                val intent = myRepository.startLoginWithGoogle(context)
                launcher.launch(intent)
            } catch (e: Exception) {
                LoginState.Failure(e.message ?: "Unknown Error Occurred")
            }
        }
    }
    fun onSignInResult(result: ActivityResult) {
        viewModelScope.launch {
            val loginResult = myRepository.handleSignIn(result)
            Log.d("LoginViewModel", "onSignInResult: $loginResult")
            loginState = if (loginResult.data != null) {
                val request = api.postLoginRequest(
                    postLoginData = PostLoginData(
                        accessToken = myRepository.getCurrentUserIdToken() ?: ""
                    )
                )
                if (request.isSuccessful) {
                    request.body()?.authentication?.payload?.user?._id?.let {
                        UserUtils.saveUserID(
                            it
                        )
                    }
                    LoginState.Success(loginResult)
                } else {
                    LoginState.Failure(request.message())
                }
            } else {
                LoginState.Failure(loginResult.errorMessage ?: "Unknown Error Occurred")
            }
        }
    }
}