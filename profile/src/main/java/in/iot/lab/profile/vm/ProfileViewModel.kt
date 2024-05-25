package `in`.iot.lab.profile.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.profile.view.event.ProfileEvents
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import `in`.iot.lab.teacherreview.domain.repository.AuthRepo
import `in`.iot.lab.teacherreview.domain.repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepo: UserRepo,
    private val authRepo: AuthRepo
) : ViewModel() {


    /**
     * This variable contains the profile data of the user fetched from the backend database.
     */
    private val _profile: MutableStateFlow<UiState<RemoteUser>> = MutableStateFlow(UiState.Idle)
    val profile = _profile.asStateFlow()


    /**
     * This function fetches the profile data from the backend database.
     */
    private fun fetchProfileData() {
        viewModelScope.launch {
            profileRepo.getUserData().collect {
                _profile.value = it.toUiState()
            }
        }
    }


    private val _logOutState: MutableStateFlow<UiState<Unit>> = MutableStateFlow(UiState.Idle)
    val logOutState = _logOutState.asStateFlow()

    private fun signOutUser() {
        viewModelScope.launch {
            authRepo.logOutUser().collect {
                _logOutState.value = it.toUiState()
            }
        }
    }


    private val _deleteAccountState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Idle)
    val deleteAccountState = _deleteAccountState.asStateFlow()


    private fun deleteAccount() {

        viewModelScope.launch {
            profileRepo.deleteUserData().collect {
                _deleteAccountState.value = it.toUiState()
            }
        }
    }


    /**
     * This function handles the user events.
     */
    fun uiListener(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.FetchUserData -> fetchProfileData()
            is ProfileEvents.SignOutEvent -> signOutUser()
            is ProfileEvents.DeleteAccountEvent -> deleteAccount()
        }
    }
}