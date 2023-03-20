package `in`.iot.lab.teacherreview.feature_authentication.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostSignupData
import `in`.iot.lab.teacherreview.feature_authentication.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.RegisterScreen
import `in`.iot.lab.teacherreview.feature_authentication.util.RegistrationState
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 *
 * This is the Register Screen [RegisterScreen]'s State holder or viewModel Class which feeds Data
 * and state to the [RegisterScreen] UI layer
 *
 * @property myRepository variable to the Object of the Repository
 * @property userInputName maintains the value of the Name of the User
 * @property userInputPhoneNumber maintains the value of the Phone Number of the User
 * @property userInputEmail This contains the Email inputted by the User
 * @property userInputEnterPassword maintains the value of the Password of the User
 * @property userInputReEnterPassword maintains the value of the Re-Entered Password of the User
 * @property showEnterPassword maintains whether the App shows the Password Typed or not
 * @property showReEnterPassword maintains whether the App shows the Re-Entered Password typed or not
 * @property registrationState This contains the Current state of the UI
 *
 * @property updateUserInputName This function updates the Name of the User entered by User
 * @property updateUserInputPhoneNumber This function updates the Phone Number entered by The User
 * @property updateUserInputEmail This function updates the Email Id entered by the User
 * @property updateEnterPassword This function updates the password entered by the User
 * @property updateReEnterPassword This function updates the Password re-entered by the User
 * @property enterPasswordShowState This function returns the VisualTransformation of the Enter
 * Password
 * @property reEnterPasswordShowState This function returns the VisualTransformation of the
 * Re-Entered Password of the User
 * @property clearUserInputName This Function Clears the User Input Name
 * @property clearUserInputEmail This Function Clears the User Input Email
 * @property clearUserInputPhoneNumber This Function Clears the User Input Phone Number
 * @property changeEnterPasswordStatus This function updates the Password state of the Enter Password Field
 * @property changeReEnterPasswordStatus This function updates the Password state of the re-Enter Password Field
 * @property resetToDefaults This Function resets all their values to default
 * @property sendRegisterRequest This Function sends the Register Request to the Backend Server
 */

class RegisterViewModel : ViewModel() {

    // Making the Repository Variable
    private val myRepository = Repository()

    // Variable which stores the User Name entered by the User
    var userInputName: String by mutableStateOf("")
        private set

    // Variable which stores the Phone Number entered by the User
    var userInputPhoneNumber: String by mutableStateOf("")
        private set

    // This contains the Email inputted by the User
    var userInputEmail: String by mutableStateOf("")
        private set

    // Variable which stores the Password Entered by the User
    var userInputEnterPassword: String by mutableStateOf("")
        private set

    // Variable which stores the Password Re-Entered by the User
    var userInputReEnterPassword: String by mutableStateOf("")
        private set

    // Variable which records whether to show the Password Entered by the User
    var showEnterPassword: Boolean by mutableStateOf(false)
        private set

    // Variable which records whether to show the Password Re-Entered by the User
    var showReEnterPassword: Boolean by mutableStateOf(false)
        private set

    // Variable keeps track of the State of the API Request
    var registrationState: RegistrationState by mutableStateOf(RegistrationState.Initialized)
        private set

    // This function updates the Name of the User entered by User
    fun updateUserInputName(newName: String) {
        userInputName = newName
    }

    // This function updates the Phone Number entered by The User
    fun updateUserInputPhoneNumber(newNo: String) {
        userInputPhoneNumber = newNo
    }

    // This function updates the Email Id entered by the User
    fun updateUserInputEmail(newEmail: String) {
        userInputEmail = newEmail
    }

    // This function updates the password entered by the User
    fun updateEnterPassword(newPass: String) {
        userInputEnterPassword = newPass
    }

    // This function updates the Password re-entered by the User
    fun updateReEnterPassword(newPass: String) {
        userInputReEnterPassword = newPass
    }


    // This function returns the VisualTransformation of the Enter Password
    fun enterPasswordShowState(): VisualTransformation {
        if (showEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    // This function returns the VisualTransformation of the Re-Entered Password of the User
    fun reEnterPasswordShowState(): VisualTransformation {
        if (showReEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    // This Function Clears the User Input Name
    fun clearUserInputName() {
        userInputName = ""
    }

    fun clearUserInputEmail() {
        userInputEmail = ""
    }

    // This function clears the User Input Phone Number
    fun clearUserInputPhoneNumber() {
        userInputPhoneNumber = ""
    }

    // This function updates the Password state of the Enter Password Field
    fun changeEnterPasswordStatus() {
        showEnterPassword = !showEnterPassword
    }

    // This function updates the Password state of the Re-Enter Password Field
    fun changeReEnterPasswordStatus() {
        showReEnterPassword = !showReEnterPassword
    }

    // This Function resets all their values to default
    fun resetToDefaults() {
        userInputName = ""
        userInputPhoneNumber = ""
        userInputEmail = ""
        userInputEnterPassword = ""
        userInputReEnterPassword = ""

        showEnterPassword = false
        showReEnterPassword = false
        registrationState = RegistrationState.Initialized
    }

    // This Function sends the Register Request to the Backend Server
    fun sendRegisterRequest() {

        // Setting the LoginState to Loading
        registrationState = RegistrationState.Loading

        if (userInputEmail.isEmpty() || userInputName.isEmpty() || userInputEnterPassword.isEmpty() || userInputReEnterPassword.isEmpty() || userInputPhoneNumber.isEmpty()) {
            registrationState = RegistrationState.Failure(errorMessage = "Enter All the Data")
            return
        }
        if (userInputEnterPassword != userInputReEnterPassword) {
            registrationState = RegistrationState.Failure(errorMessage = "Passwords doesn't Match")
            return
        }

        val postRegistrationData = PostSignupData(
            name = userInputName,
            email = userInputEmail,
            password = userInputEnterPassword
        )

        viewModelScope.launch {

            registrationState = try {

                // Response from the Repository Layer
                val response = myRepository.postSignupRequest(postRegistrationData)

                // Updating the Login State accordingly
                if (response is RegistrationState.Success)
                    RegistrationState.Success(response.data)
                else
                    RegistrationState.Failure("Login Failed !!")

            } catch (_: ConnectException) {
                RegistrationState.Failure("Internet Not Available !!")
            }
        }
    }
}