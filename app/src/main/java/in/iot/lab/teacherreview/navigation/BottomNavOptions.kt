package `in`.iot.lab.teacherreview.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import `in`.iot.lab.history.view.navigation.HISTORY_ROUTE
import `in`.iot.lab.profile.view.navigation.PROFILE_ROUTE
import `in`.iot.lab.review.view.navigation.REVIEW_ROUTE


sealed class BottomNavOptions(
    val route: String,
    val labelOfIcon: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val onOptionClicked: (NavController) -> Unit
) {

    data object ReviewOption : BottomNavOptions(
        route = REVIEW_ROUTE,
        labelOfIcon = "Home",
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        onOptionClicked = {
            it.navigate(REVIEW_ROUTE) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )

    data object HistoryOption : BottomNavOptions(
        route = HISTORY_ROUTE,
        labelOfIcon = "History",
        unselectedIcon = Icons.Outlined.History,
        selectedIcon = Icons.Filled.History,
        onOptionClicked = {
            it.navigate(HISTORY_ROUTE) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )

    data object ProfileOption : BottomNavOptions(
        route = PROFILE_ROUTE,
        labelOfIcon = "Profile",
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        onOptionClicked = {
            it.navigate(PROFILE_ROUTE) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )


    companion object {
        val bottomNavOptions = listOf(
            ReviewOption,
            HistoryOption,
            ProfileOption
        )
    }
}