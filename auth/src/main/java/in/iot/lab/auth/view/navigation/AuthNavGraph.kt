package `in`.iot.lab.auth.view.navigation


import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.auth.view.screens.AuthScreenControl
import `in`.iot.lab.auth.vm.AuthViewModel


// Auth Route
const val AUTH_ROUTE = "auth-base-route"


fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    this.navigate(AUTH_ROUTE) {
        navOptions.apply {
            popUpTo(AUTH_ROUTE) {
                inclusive = false
            }
        }
    }
}


fun NavGraphBuilder.authNavGraph(onSignedIn: () -> Unit) {


    // Auth Screen
    composable(AUTH_ROUTE) {

        // Auth View Model Instance
        val authViewModel: AuthViewModel = hiltViewModel()
        val authApiState = authViewModel.authApiState.collectAsState().value

        AuthScreenControl(
            authApiState = authApiState,
            onSignInSuccess = onSignedIn,
            setEvent = authViewModel::uiListener
        )
    }
}