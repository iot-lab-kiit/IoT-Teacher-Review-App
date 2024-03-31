package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.navigation.TeacherListNavGraph
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.HistoryScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.ProfileScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder.HistoryScreenViewModel
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder.ProfileScreenViewModel

/**
 * Navigation Graph : It contains all the Different Routes in the bottom Navigation
 */
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    profileVm: ProfileScreenViewModel = hiltViewModel(),
    historyVm: HistoryScreenViewModel = hiltViewModel()
) {

    val currentUserState = profileVm.currentUser.collectAsState().value
    NavHost(
        navController = navController,
        startDestination = BottomNavRoutes.HomeRoute.route,
        builder = {

            // Teacher List Bottom Navigation Option
            composable(
                BottomNavRoutes.HomeRoute.route,
                content = {

                    val teacherListNavController = rememberNavController()

                    TeacherListNavGraph(
                        navController = teacherListNavController,
                    )
                }
            )

            // History Bottom Navigation Option
            composable(
                BottomNavRoutes.HistoryRoute.route,
                content = { HistoryScreenControl(
                    historyActions = historyVm::historyAction,
                    getHistoryApiCallState = historyVm.getHistoryApiCallState,
                    userIdFlow = currentUserState?.uid.toString()
                ) }
            )

            // Profile Bottom Navigation Options
            composable(
                BottomNavRoutes.ProfileRoute.route,
                content = {
                    ProfileScreen(
                        navController = navController,
                        profileAction = profileVm::profileAction,
                        userPhoto = currentUserState?.photoUrl.toString(),
                        userEmail = currentUserState?.email.toString(),
                        userUserName = currentUserState?.username.toString()
                    )
                }
            )
        }
    )
}