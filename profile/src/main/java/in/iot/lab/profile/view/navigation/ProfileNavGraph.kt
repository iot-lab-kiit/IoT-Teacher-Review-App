package `in`.iot.lab.profile.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.profile.view.screens.ProfileScreenControl

const val PROFILE_ROUTE = "profile-root-route"


fun NavGraphBuilder.profileNavGraph(onSignOutClick: () -> Unit) {

    composable(PROFILE_ROUTE) {
        ProfileScreenControl()
    }
}