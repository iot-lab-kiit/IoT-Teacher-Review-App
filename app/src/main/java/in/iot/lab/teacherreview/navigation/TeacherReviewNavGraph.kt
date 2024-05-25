package `in`.iot.lab.teacherreview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import `in`.iot.lab.auth.view.navigation.AUTH_ROUTE
import `in`.iot.lab.auth.view.navigation.authNavGraph
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.navigateToHome

@Composable
fun TeacherReviewNavGraph(
    navHostController: NavHostController = rememberNavController(),
    isUserLoggedIn: Boolean,
) {
    val initialRoute = AUTH_ROUTE
//        if (isUserLoggedIn) HOME_ROOT
//        else LOGIN_ROUTE


    NavHost(
        navController = navHostController,
        startDestination = initialRoute,
    ) {

        authNavGraph {
            navHostController.navigateToHome(
                navOptions = navOptions {
                    popUpTo(AUTH_ROUTE) {
                        inclusive = true
                    }
                }
            )
        }

//        homeNavGraph()
    }
}