package `in`.iot.lab.kritique.view.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.kritique.view.navigation.BottomNavOptions
import `in`.iot.lab.kritique.view.navigation.BottomNavOptions.Companion.bottomNavOptions


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

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomMenu: List<BottomNavOptions>
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A1020).copy(alpha = 0.0f),
                        Color(0xFF080D1A).copy(alpha = 0.97f)
                    )
                )
            ),
        containerColor = Color(0xFF0B1220).copy(alpha = 0.95f),
        tonalElevation = 0.dp,
    ) {
        for (menuItem in bottomMenu) {

            val selected =
                (menuItem.route == backStackEntry.value?.destination?.parent?.route) ||
                        (menuItem.route == backStackEntry.value?.destination?.route)

            NavigationBarItem(
                selected = selected,
                onClick = { menuItem.onOptionClicked(navController) },
                icon = {
                    Icon(
                        imageVector = if (selected)
                            menuItem.selectedIcon
                        else
                            menuItem.unselectedIcon,
                        contentDescription = menuItem.labelOfIcon
                    )
                },
                label = {
                    Text(
                        text = menuItem.labelOfIcon,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    // Active icon + label: vivid blue
                    selectedIconColor   = Color.White,
                    selectedTextColor   = primaryColor,
                    // Active pill: subtle blue glass
                    indicatorColor      = primaryColor.copy(alpha = 0.18f),
                    // Inactive icon + label: muted blue-grey
                    unselectedIconColor = Color(0xFF4A6080),
                    unselectedTextColor = Color(0xFF4A6080),
                )
            )
        }
    }
}