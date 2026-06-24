package `in`.iot.lab.kritique.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.design.theme.primaryColor
import `in`.iot.lab.design.theme.secondaryColor
import `in`.iot.lab.kritique.view.navigation.BottomNavOptions
import `in`.iot.lab.kritique.view.navigation.BottomNavOptions.Companion.bottomNavOptions

@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        val hazeState = remember { HazeState() }
        BottomNavBar(
            navController = rememberNavController(),
            bottomMenu = bottomNavOptions,
            hazeState = hazeState
        )
    }
}

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomMenu: List<BottomNavOptions>,
    hazeState: HazeState
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier.hazeEffect(
            state = hazeState,
            style = HazeStyle(
                backgroundColor = Color(0xFF000000),
                tints = listOf(
                    HazeTint(color = primaryColor.copy(alpha = 0.08f)),
                    HazeTint(color = secondaryColor.copy(alpha = 0.05f))
                ),
                blurRadius = 24.dp,
                noiseFactor = 0.05f
            )
        ),
        containerColor = Color(0xFF08080F).copy(alpha = 0.55f),
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        for (menuItem in bottomMenu) {
            val selected =
                menuItem.route == backStackEntry.value?.destination?.parent?.route ||
                        menuItem.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { menuItem.onOptionClicked(navController) },
                icon = {
                    Icon(
                        imageVector = if (selected) menuItem.selectedIcon else menuItem.unselectedIcon,
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
                    selectedIconColor   = primaryColor,
                    selectedTextColor   = primaryColor,
                    indicatorColor      = secondaryColor.copy(alpha = 0.15f),
                    unselectedIconColor = Color(0xFF3D4270),
                    unselectedTextColor = Color(0xFF3D4270)
                )
            )
        }
    }
}