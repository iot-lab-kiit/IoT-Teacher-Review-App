package `in`.iot.lab.teacherreview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import `in`.iot.lab.auth.view.navigation.AUTH_ROUTE
import `in`.iot.lab.auth.view.navigation.authNavGraph
import `in`.iot.lab.auth.view.navigation.navigateToAuth
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.homeNavGraph
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.navigateToHome

@Composable
fun MainNavGraph(
    navHostController: NavHostController = rememberNavController(),
    isUserLoggedIn: Boolean,
) {

    // Initial Route according to the user login status.
    val initialRoute = when (isUserLoggedIn) {
        true -> HOME_ROOT_ROUTE
        else -> AUTH_ROUTE
    }


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

        composable(HOME_ROOT_ROUTE) {
            HomeNavGraph {
                navHostController.navigateToAuth(
                    navOptions = navOptions {
                        popUpTo(AUTH_ROUTE) {
                            inclusive = false
                        }
                    }
                )
            }
        }

        homeNavGraph()
    }
}