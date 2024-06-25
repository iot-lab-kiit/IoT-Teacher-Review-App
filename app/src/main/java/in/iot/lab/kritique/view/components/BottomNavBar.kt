package `in`.iot.lab.kritique.view.components

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.kritique.view.navigation.BottomNavOptions
import `in`.iot.lab.kritique.view.navigation.BottomNavOptions.Companion.bottomNavOptions


// This is the Preview function of the Bottom Navigation Bar
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        BottomNavBar(
            navController = rememberNavController(),
            bottomMenu = bottomNavOptions
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
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomMenu: List<BottomNavOptions>
) {

    // This Composable makes the Bottom Navigation Bar

    NavigationBar(modifier = modifier) {

        // This Variable keeps track of the Latest BackStack Entry
        val backStackEntry = navController.currentBackStackEntryAsState()

        // Accessing each Bottom Navigation Options
        for (menuItem in bottomMenu) {

            // Checking if the Current Route and the Item is Same so that the item can be decorated differently
            val selected = (menuItem.route == backStackEntry.value?.destination?.parent?.route)
                    || (menuItem.route == backStackEntry.value?.destination?.route)

            // This Composable draws each Bottom Navigation Option
            NavigationBarItem(
                selected = selected,
                onClick = {
                    menuItem.onOptionClicked(navController)
                },
                icon = {
                    val currentIcon = if (selected)
                        menuItem.selectedIcon
                    else
                        menuItem.unselectedIcon

                    Icon(
                        imageVector = currentIcon,
                        contentDescription = menuItem.labelOfIcon
                    )
                },
                label = { Text(text = menuItem.labelOfIcon) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}