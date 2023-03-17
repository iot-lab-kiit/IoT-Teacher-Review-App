package `in`.iot.lab.teacherreview.feature_authentication.util

import `in`.iot.lab.teacherreview.feature_authentication.data.models.UserAuthentication

/**
 * This sealed Class contains all the States of the Login Request in a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in fetching Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class LoginState{
    object Initialized : LoginState()
    object Loading : LoginState()
    class Success(val data : UserAuthentication) : LoginState()
    class Failure(val errorMessage : String) : LoginState()
}