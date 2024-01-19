package `in`.iot.lab.teacherreview.feature_authentication.presentation.navigation

/**
 * This class contains the Several Routes that can be taken for navigation
 *
 * @property Login this is the route to the login screen
 */
sealed class AuthenticationRoutes(val route : String){
    object Login : AuthenticationRoutes("login-screen")
}