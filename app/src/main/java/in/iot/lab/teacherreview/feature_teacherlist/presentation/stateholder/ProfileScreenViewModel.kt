package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.LocalUser
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.action.ProfileActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _currentUser: MutableStateFlow<LocalUser?> = MutableStateFlow(null)
    val currentUser: StateFlow<LocalUser?> = _currentUser

    init {
        viewModelScope.launch {
            getCurrentUser()
        }
    }

    private suspend fun getCurrentUser() {
        _currentUser.value = repository.getCurrentUser().getOrNull()
    }

    fun signOut() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun profileAction(profileActions: ProfileActions) {
        when (profileActions) {
            ProfileActions.GetCurrentUser -> getCurrentUser()
            ProfileActions.SignOut -> signOut()

        }
    }

}