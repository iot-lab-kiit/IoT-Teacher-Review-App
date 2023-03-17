package `in`.iot.lab.teacherreview.feature_authentication.presentation.navigation

sealed class AuthenticationNavigationRoutes(val routes : String){
    object Login : AuthenticationNavigationRoutes("login-screen")
    object Register : AuthenticationNavigationRoutes("register-screen")
    object ForgotPassword : AuthenticationNavigationRoutes("forgot-password")
}