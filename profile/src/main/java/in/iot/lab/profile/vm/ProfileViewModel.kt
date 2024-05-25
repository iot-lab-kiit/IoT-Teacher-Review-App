package `in`.iot.lab.profile.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.profile.view.event.ProfileEvents
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {


    private val _profile: MutableStateFlow<UiState<RemoteUser>> = MutableStateFlow(UiState.Idle)
    val profile = _profile.asStateFlow()


    private fun fetchProfileData() {
        viewModelScope.launch {
            _profile.value = UiState.Loading
        }
    }


    private fun signOutUser() {

    }


    private fun deleteAccount() {

    }


    fun uiListener(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.FetchUserData -> fetchProfileData()
            is ProfileEvents.SignOutEvent -> signOutUser()
            is ProfileEvents.DeleteAccountEvent -> deleteAccount()
        }
    }
}