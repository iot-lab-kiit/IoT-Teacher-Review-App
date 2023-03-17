package `in`.iot.lab.teacherreview.feature_authentication.presentation.navigation

/**
 * This class contains the Several Routes that can be taken for navigation
 *
 * @property Login this is the route to the login screen
 * @property Register this is the route to the Register Screen
 * @property ForgotPassword this is the route to the Forgot Password Screen
 */
sealed class AuthenticationRoutes(val route : String){
    object Login : AuthenticationRoutes("login-screen")
    object Register : AuthenticationRoutes("register-screen")
    object ForgotPassword : AuthenticationRoutes("forgot-password")
}