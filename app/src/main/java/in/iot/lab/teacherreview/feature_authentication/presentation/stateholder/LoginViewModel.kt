package `in`.iot.lab.teacherreview.feature_authentication.presentation.stateholder

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.LoginScreen
import `in`.iot.lab.teacherreview.feature_authentication.util.LoginState
import kotlinx.coroutines.launch
import java.net.ConnectException

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

    // Making the Repository Variable
    private val myRepository = Repository()

    // Variable keeps track of the State of the API Request
    var loginState: LoginState by mutableStateOf(LoginState.Initialized)
        private set


    // This Function is Executed to send the Login Request to the Backend Server
    fun sendLoginRequest(context: Context) {

        // Setting the LoginState to Loading
        loginState = LoginState.Loading

        // Requesting the Server to Check the user login Credentials
        viewModelScope.launch {

//            // Fake Implementation for Testing
//            loginState = LoginState.Success(
//                AuthenticationResponse(
//                    accessToken = "",
//                    user = UserData(
//                        _id = "",
//                        email = "",
//                        name = "",
//                        role = 0,
//                        status = 0
//                    )
//                )
//            )

            // TODO :- ---- Real Implementation which is commented out for the Moment


            // Try is used to catch Exception which will occur when the Internet is unavailable
            loginState = try {

                // Response from the Repository Layer
                val response = myRepository.postLoginRequest(postLoginData)

                // Updating the Login State accordingly
                if (response is LoginState.Success) {

                    // Setting the Student ID in the Constants Folder
                    Constants.setStudentId(response.data.user._id)

                    // Setting the API Call state
                    LoginState.Success(response.data)
                } else
                    LoginState.Failure("Login Failed !!")

            } catch (_: ConnectException) {
                LoginState.Failure("Internet Not Available !!")
            }
        }
    }
}