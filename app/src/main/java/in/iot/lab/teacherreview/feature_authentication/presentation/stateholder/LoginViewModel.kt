package `in`.iot.lab.teacherreview.feature_authentication.presentation.stateholder

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.feature_authentication.data.data_source.remote.RetrofitInstance
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.LoginScreen
import `in`.iot.lab.teacherreview.feature_authentication.util.LoginState
import kotlinx.coroutines.launch

/**
 * This is the Login Screen [LoginScreen]'s State holder or viewModel Class which feeds Data
 * and state to the [LoginScreen] UI layer
 *
 * @property myRepository Repository Variable
 * @property loginState keeps a track of the Current State of the Login API Request
 *
 * @property sendLoginRequest sends the Login Request to be handled by the Repository Layer
 */

class LoginViewModel : ViewModel() {
    private val api = RetrofitInstance.apiInstance

    // Making the Repository Variable
    private val myRepository = Repository()

    // Variable keeps track of the State of the API Request
    var loginState: LoginState by mutableStateOf(LoginState.Initialized)
        private set

    // This Function is Executed to check if the User is Logged In
    fun checkIfUserIsLoggedIn(): Boolean {
        return myRepository.checkIfUserIsLoggedIn()
    }
    // This Function is Executed to send the Login Request to the Backend Server
    fun sendLoginRequest(context: Context) {
        viewModelScope.launch {
            try {
                // Setting the LoginState to Loading
                loginState = LoginState.Loading
                val result = myRepository.startLoginWithGoogle(context)
                loginState = if (result.data != null) {
                    // Sending the Login Request to the Backend Server
                    val request = api.postLoginRequest(
                        postLoginData = PostLoginData(
                            accessToken = myRepository.getCurrentUserIdToken() ?: ""
                        )
                    )
                    if (request.isSuccessful) {
                        // Setting the LoginState to Success
                        LoginState.Success(result)
                    } else {
                        // Setting the LoginState to Failure
                        LoginState.Failure(request.message())
                    }
                } else {
                    // Setting the LoginState to Failure
                    LoginState.Failure(result.errorMessage ?: "Unknown Error Occurred")
                }
            } catch (e: Exception) {
                LoginState.Failure(e.message ?: "Unknown Error Occurred")
            }
        }
    }
}