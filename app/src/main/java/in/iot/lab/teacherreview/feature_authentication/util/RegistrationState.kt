package `in`.iot.lab.teacherreview.feature_authentication.util

import `in`.iot.lab.teacherreview.feature_authentication.data.models.UserData
import `in`.iot.lab.teacherreview.feature_authentication.util.RegistrationState.*

/**
 * This sealed Class contains all the States of the Registration Request in a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in fetching Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class RegistrationState{
    object Initialized : RegistrationState()
    object Loading : RegistrationState()
    class Success(val data : UserData) : RegistrationState()
    class Failure(val errorMessage : String) : RegistrationState()
}