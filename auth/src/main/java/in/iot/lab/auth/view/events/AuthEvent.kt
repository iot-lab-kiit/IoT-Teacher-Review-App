package `in`.iot.lab.auth.view.events

import com.google.firebase.auth.AuthCredential
import java.lang.Exception

sealed class AuthEvent {

    data class LoginUser(val authCredential: AuthCredential) : AuthEvent()
    data class ExceptionFound(val exception: Exception) : AuthEvent()
}