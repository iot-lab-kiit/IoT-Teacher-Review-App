package `in`.iot.lab.teacherreview.feature_authentication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.teacherreview.feature_authentication.presentation.screen.LoginScreen

/**
 * Navigation Graph : It contains all the Different Routes in the Authentication Feature
 */
@Composable
fun AuthenticationNavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination = AuthenticationRoutes.Login.route, builder = {
        composable(AuthenticationRoutes.Login.route, content = { LoginScreen(navController = navController) })
    })
}