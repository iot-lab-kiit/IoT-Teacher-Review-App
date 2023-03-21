package `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavOptions.Companion.menuItems

// This is the Preview function of the Bottom Navigation Bar
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        BottomBar(
            navController = rememberNavController(),
            bottomMenu = menuItems
        )
    }
}

/**
 * This is the Function which makes the Bottom App Bar
 *
 * @param modifier Default Modifier so that the Parent Function can send something if needed
 * @param navController This is the NavController which we will be using to navigate the Screens
 * @param bottomMenu This Menu Contains the Data about the various routes in the Bottom Route
 */
@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomMenu: List<BottomNavOptions>
) {

    // This Composable makes the Bottom Navigation Bar
    NavigationBar(
        modifier = modifier,
        tonalElevation = 16.dp,
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {

        // This Variable keeps track of the Latest BackStack Entry
        val backStackEntry = navController.currentBackStackEntryAsState()

        // Accessing each Bottom Navigation Options
        for (menuItem in bottomMenu) {

            // Checking if the Current Route and the Item is Same so that the item can be decorated differently
            val selected = menuItem.route == backStackEntry.value?.destination?.route

            // This Composable draws each Bottom Navigation Option
            NavigationBarItem(
                selected = selected,
                onClick = {
                    menuItem.onOptionClicked(navController)
                },
                icon = {

                    // The current icon to be showed in the bottom navigation menu Item
                    val currentIcon = if (selected)
                        menuItem.selectedIcon
                    else
                        menuItem.unselectedIcon

                    Icon(
                        imageVector = currentIcon,
                        contentDescription = stringResource(id = menuItem.labelOfIcon),
//                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                label = {
                    if (selected) {
                        Text(
                            text = stringResource(id = menuItem.labelOfIcon)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(

                    // TODO :- Subject To change
//                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            )
        }
    }
}