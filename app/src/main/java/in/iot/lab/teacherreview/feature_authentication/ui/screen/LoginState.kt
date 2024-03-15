package `in`.iot.lab.teacherreview.feature_authentication.ui.screen

import `in`.iot.lab.teacherreview.feature_authentication.domain.models.LocalUser

sealed interface LoginState {
    data object Initialized: LoginState
    data object Loading: LoginState
    data class Success(val data: LocalUser? = null): LoginState
    data class Failure(val message: String): LoginState
}