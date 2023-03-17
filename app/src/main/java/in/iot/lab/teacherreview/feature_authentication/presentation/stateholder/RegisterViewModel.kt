package `in`.iot.lab.teacherreview.feature_authentication.presentation.stateholder

import android.util.Log.d
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.RegisterScreen

/**
 *
 * This is the Register Screen [RegisterScreen]'s State holder or viewModel Class which feeds Data
 * and state to the [RegisterScreen] UI layer
 *
 * @property userInputName maintains the value of the Name of the User
 * @property userInputPhoneNumber maintains the value of the Phone Number of the User
 * @property userInputEnterPassword maintains the value of the Password of the User
 * @property userInputReEnterPassword maintains the value of the Re-Entered Password of the User
 * @property showEnterPassword maintains whether the App shows the Password Typed or not
 * @property showReEnterPassword maintains whether the App shows the Re-Entered Password typed or not
 *
 */

class RegisterViewModel : ViewModel() {

    // Variable which stores the User Name entered by the User
    var userInputName : String by mutableStateOf("")
        private set

    // Variable which stores the Phone Number entered by the User
    var userInputPhoneNumber : String by mutableStateOf("")
        private set

    // Variable which stores the Password Entered by the User
    var userInputEnterPassword : String by mutableStateOf("")
        private set

    // Variable which stores the Password Re-Entered by the User
    var userInputReEnterPassword : String by mutableStateOf("")
        private set

    // Variable which records whether to show the Password Entered by the User
    var showEnterPassword : Boolean by mutableStateOf(false)
        private set

    // Variable which records whether to show the Password Re-Entered by the User
    var showReEnterPassword : Boolean by mutableStateOf(false)
        private set

    // This function updates the Name of the User entered by User
    fun updateName(newName : String){
        userInputName = newName
    }

    // This function updates the Phone Number entered by The User
    fun updatePhoneNumber(newNo : String){
        userInputPhoneNumber = newNo
    }

    // This function updates the password entered by the User
    fun updateEnterPassword(newPass : String){
        userInputEnterPassword = newPass
    }

    // This function updates the Password re-entered by the User
    fun updateReEnterPassword(newPass : String){
        userInputReEnterPassword = newPass
    }

    // This function returns the VisualTransformation of the Enter Password
    fun enterPasswordShowState() : VisualTransformation {
        if(showEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    // This function returns the VisualTransformation of the Re-Entered Password of the User
    fun reEnterPasswordShowState() : VisualTransformation {
        if(showReEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    // This Function Clears the User Input Name
    fun clearUserInputName() {
        userInputName = ""
    }

    // This function clears the User Input Phone Number
    fun clearUserInputPhoneNumber(){
        userInputPhoneNumber = ""
    }

    // This function updates the Password state of the Enter Password Field
    fun changeEnterPasswordStatus(){
        showEnterPassword = !showEnterPassword
    }

    // This function updates the Password state of the Re-Enter Password Field
    fun changeReEnterPasswordStatus(){
        showReEnterPassword = !showReEnterPassword
    }

    // This Function sends the Register Request to the Backend Server
    fun sendRegisterRequest(){

        // TODO :- Real Function is yet to be Implemented

        d("Register View Model" , "$userInputName : $userInputPhoneNumber : $userInputEnterPassword : $userInputReEnterPassword")
    }

    // This Function resets all their values to default
    fun resetToDefaults() {
        userInputName = ""
        userInputPhoneNumber = ""
        userInputEnterPassword = ""
        userInputReEnterPassword = ""

        showEnterPassword = false
        showReEnterPassword = false
    }
}