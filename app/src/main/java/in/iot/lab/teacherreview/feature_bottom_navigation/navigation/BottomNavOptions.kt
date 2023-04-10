package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavOptions.*
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavOptions.Companion.menuItems

/**
 * This class will contain all the Various Screens related to the bottom Navigation bar
 *
 * @param route It keeps the Route to the Option
 * @param labelOfIcon It keeps the Label of the String
 * @param unselectedIcon It is the ImageVector which we have to show when the option is not selected
 * @param selectedIcon It is the ImageVector which we have to show when the option is selected
 * @param onOptionClicked It is a function which executes when we click this button to navigate
 *
 * @property menuItems This is a list of all the menu Items
 * @property HomeOption This is the option which contains the data of the Home Menu Item
 * @property HistoryOption This is the option which contains the data of the History Menu Item
 * @property ProfileOption This is the option which contains the data of the Profile Menu Item
 */
sealed class BottomNavOptions(
    val route: String,
    @StringRes val labelOfIcon: Int,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val onOptionClicked: (NavController) -> Unit
) {

    // This is made companion to let everyone in the App to get to User
    companion object {
        val menuItems = listOf(
            HomeOption,
            HistoryOption,
            ProfileOption
        )
    }

    // Teacher List Option for Going
    object HomeOption : BottomNavOptions(
        route = BottomNavRoutes.HomeRoute.route,
        labelOfIcon = R.string.home,
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        onOptionClicked = {
            it.navigate(BottomNavRoutes.HomeRoute.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )

    // Student Review History Option for Going
    object HistoryOption : BottomNavOptions(
        route = BottomNavRoutes.HistoryRoute.route,
        labelOfIcon = R.string.history,
        unselectedIcon = Icons.Outlined.Email,
        selectedIcon = Icons.Filled.Email,
        onOptionClicked = {
            it.navigate(BottomNavRoutes.HistoryRoute.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )

    // Profile Option to go For
    object ProfileOption : BottomNavOptions(
        route = BottomNavRoutes.ProfileRoute.route,
        labelOfIcon = R.string.profile,
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        onOptionClicked = {
            it.navigate(BottomNavRoutes.ProfileRoute.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )
}
