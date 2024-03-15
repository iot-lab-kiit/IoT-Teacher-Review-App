package `in`.iot.lab.teacherreview.feature_authentication.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import `in`.iot.lab.teacherreview.feature_authentication.ui.screen.LoginScreen

internal const val LOGIN_ROUTE = "login-screen"
fun NavController.navigateToLogin(navOptions: NavOptions) = navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginScreen(
    onUserSignedIn: () -> Unit = {}
) {
    composable(
        route = LOGIN_ROUTE
    ) {
        LoginScreen(onUserSignedIn = onUserSignedIn)
    }
}