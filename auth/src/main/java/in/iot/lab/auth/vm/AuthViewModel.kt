package `in`.iot.lab.auth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.auth.view.events.AuthEvent
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.teacherreview.domain.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _authApiState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Idle)
    val authApiState = _authApiState.asStateFlow()

    private fun loginUser(authCredential: AuthCredential) {
        viewModelScope.launch {
            authRepo.loginUser(authCredential).collect {
                _authApiState.value = it.toUiState()
            }
        }
    }


    fun uiListener(event: AuthEvent) {
        when (event) {
            is AuthEvent.LoginUser -> loginUser(event.authCredential)

            is AuthEvent.ExceptionFound -> {
                _authApiState.value = UiState.Failed(event.exception.message.toString())
            }
        }
    }
}