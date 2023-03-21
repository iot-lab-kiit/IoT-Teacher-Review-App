package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavRoutes.*
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HomeScreenControl

/**
 * This File contains all the Routes inside the Bottom Navigation Bar
 *
 * @property HomeRoute This is the Route to the [HomeScreenControl]
 * @property HistoryRoute This is the route to the History Screen
 * @property ProfileRoute This is the route to the Profile Screen
 */
sealed class BottomNavRoutes(val route: String) {
    object HomeRoute : BottomNavRoutes("home-screen")
    object HistoryRoute : BottomNavRoutes("history-screen")
    object ProfileRoute : BottomNavRoutes(route = "profile-screen")
}