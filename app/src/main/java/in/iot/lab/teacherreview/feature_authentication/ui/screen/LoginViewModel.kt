package `in`.iot.lab.teacherreview.feature_authentication.ui.screen

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Initialized)
    val state = _state.asStateFlow()

    fun checkIfUserIsLoggedIn(): Boolean {
        return authRepository.isUserSignedIn()
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signIn(idToken: String) {
        viewModelScope.launch {
            try {
                // Setting the LoginState to Loading
                _state.value = LoginState.Loading
                authRepository.loginWithGoogle(idToken)
                    .onSuccess {
                        _state.value = LoginState.Success(it)
                    }
                    .onFailure {
                        _state.value = LoginState.Failure(it.message ?: "Unknown Error Occurred")
                    }
            } catch (e: Exception) {
                _state.value = LoginState.Failure(e.message ?: "Unknown Error Occurred")
            }
        }
    }
}