package `in`.iot.lab.auth.view.navigation

import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val AUTH_ROUTE = "auth-base-route"

fun NavGraphBuilder.authNavGraph(onSignedIn: () -> Unit) {

    composable(AUTH_ROUTE) {

    }
}