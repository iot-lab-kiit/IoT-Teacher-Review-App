package `in`.iot.lab.profile.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.profile.view.screens.ProfileScreenControl
import `in`.iot.lab.profile.vm.ProfileViewModel

const val PROFILE_ROUTE = "profile-root-route"


fun NavGraphBuilder.profileNavGraph(onSignOutClick: () -> Unit) {

    composable(PROFILE_ROUTE) {

        val viewModel: ProfileViewModel = hiltViewModel()
        val profile = viewModel.profile.collectAsState().value
        val deleteState = viewModel.deleteAccountState.collectAsState().value
        val signOutState = viewModel.logOutState.collectAsState().value

        ProfileScreenControl(
            userApiState = profile,
            logOutState = signOutState,
            deleteAccountState = deleteState,
            setEvent = viewModel::uiListener,
            onLogOutClick = onSignOutClick
        )
    }
}