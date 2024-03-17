package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.LocalUser
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.toLocalUser
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _currentUser: MutableStateFlow<LocalUser?> = MutableStateFlow(null)
    val currentUser: StateFlow<LocalUser?> = _currentUser

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        _currentUser.value = auth.currentUser?.toLocalUser()
    }

    fun signOut() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun profileAction(profileState: ProfileState) {
        when (profileState) {
            `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state.ProfileState.getCurrentUser -> getCurrentUser()
            `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state.ProfileState.signOut -> signOut()
        }
    }

}