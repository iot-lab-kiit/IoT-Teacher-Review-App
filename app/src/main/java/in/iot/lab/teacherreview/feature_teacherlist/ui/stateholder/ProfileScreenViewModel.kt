package `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.core.data.local.UserPreferences
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.LocalUser
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.action.ProfileActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repository: AuthRepository,
    userPreferences: UserPreferences
) : ViewModel() {

    private val _currentUser: MutableStateFlow<LocalUser?> = MutableStateFlow(null)
    val currentUser: StateFlow<LocalUser?> = _currentUser
    private val _currentUserId: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    init {
        viewModelScope.launch {
            getCurrentUser()
            userPreferences.userId.collect{
                _currentUserId.value = it
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = repository.getCurrentUser().getOrNull()
        }
    }

    private fun signOut() {
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