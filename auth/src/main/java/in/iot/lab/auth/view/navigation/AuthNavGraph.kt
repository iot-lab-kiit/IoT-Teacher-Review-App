package `in`.iot.lab.auth.view.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.auth.view.screens.AuthScreenControl


// Auth Route
const val AUTH_ROUTE = "auth-base-route"

fun NavGraphBuilder.authNavGraph(onSignedIn: () -> Unit) {


    // Auth Screen
    composable(AUTH_ROUTE) {
        AuthScreenControl()
    }
}