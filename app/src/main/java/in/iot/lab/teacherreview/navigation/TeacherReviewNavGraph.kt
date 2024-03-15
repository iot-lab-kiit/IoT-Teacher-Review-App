package `in`.iot.lab.teacherreview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import `in`.iot.lab.teacherreview.feature_authentication.ui.navigation.LOGIN_ROUTE
import `in`.iot.lab.teacherreview.feature_authentication.ui.navigation.loginScreen
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.HOME_ROOT
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.homeNavGraph
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.navigateToHome

@Composable
fun TeacherReviewNavGraph(
    navHostController: NavHostController = rememberNavController(),
    isUserLoggedIn: Boolean,
) {
    val initialRoute =
        if (isUserLoggedIn) HOME_ROOT
        else LOGIN_ROUTE


    NavHost(
        navController = navHostController,
        startDestination = initialRoute,
    ) {

        loginScreen(
            onUserSignedIn = {
                navHostController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(LOGIN_ROUTE) {
                            inclusive = true
                        }
                    }
                )
            }
        )

        homeNavGraph()
    }
}