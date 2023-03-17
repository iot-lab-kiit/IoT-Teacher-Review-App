package `in`.iot.lab.teacherreview.feature_authentication.presentation.stateholder

import android.util.Log.d
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.ForgotPasswordScreen

/**
 * This is the ForgotPassword Screen [ForgotPasswordScreen]'s State holder or viewModel Class which feeds Data
 * and state to the [ForgotPasswordScreen] UI layer
 *
 * @property userInputPhoneNumber maintains the value of the Phone Number of the User
 */

class ForgotPasswordViewModel : ViewModel() {

    // Phone Number which is entered by the User
    var userInputPhoneNumber: String by mutableStateOf("")
        private set

    // This function clears the phone Number inputted by the User
    fun clearUserInputPhoneNumber() {
        userInputPhoneNumber = ""
    }

    // This function updates the number of the User as he interacts with the User
    fun changeUserInputPhoneNumber(newNumber: String) {
        userInputPhoneNumber = newNumber
    }

    // This function does the API call and post the data
    fun sendForgotPasswordRequest() {

        // TODO :- Real Implementation Yet Remained

        d("Forgot View Model" , "Logging : $userInputPhoneNumber")
    }

    // This function is used to turn everything to their Default Parameters
    fun resetToDefault() {
        userInputPhoneNumber = ""
    }
}