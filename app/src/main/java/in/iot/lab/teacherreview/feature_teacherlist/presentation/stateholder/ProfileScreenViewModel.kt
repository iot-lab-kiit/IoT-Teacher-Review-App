package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.core.utils.UserUtils
import `in`.iot.lab.teacherreview.feature_authentication.data.models.LocalUser
import `in`.iot.lab.teacherreview.feature_authentication.data.models.toLocalUser
import `in`.iot.lab.teacherreview.feature_authentication.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel : ViewModel() {
    private val myRepository = Repository()
    private val _currentUser: MutableStateFlow<LocalUser?> = MutableStateFlow(null)
    val currentUser: StateFlow<LocalUser?> = _currentUser

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        _currentUser.value = myRepository.getCurrentUser()?.toLocalUser()
    }

    fun signOut() {
        viewModelScope.launch {
            UserUtils.deleteUserID()
            myRepository.logout()
        }
    }
}