package `in`.iot.lab.kritique.view.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.chrisbanes.haze.HazeState
import `in`.iot.lab.history.view.navigation.HISTORY_ROUTE
import `in`.iot.lab.profile.view.navigation.PROFILE_ROUTE
import `in`.iot.lab.review.view.navigation.FACULTY_ROOT_ROUTE

private fun NavController.navigateToBottomTab(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

private fun NavController.navigateToFacultyHome() {
    navigate(FACULTY_ROOT_ROUTE) {
        popUpTo(FACULTY_ROOT_ROUTE) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
    }
}

sealed class BottomNavOptions(
    val route: String,
    val labelOfIcon: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val onOptionClicked: (NavController) -> Unit
) {

    data object FacultyOption : BottomNavOptions(
        route = FACULTY_ROOT_ROUTE,
        labelOfIcon = "Home",
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        onOptionClicked = { it.navigateToFacultyHome() }
    )

    data object HistoryOption : BottomNavOptions(
        route = HISTORY_ROUTE,
        labelOfIcon = "History",
        unselectedIcon = Icons.Outlined.History,
        selectedIcon = Icons.Filled.History,
        onOptionClicked = { it.navigateToBottomTab(HISTORY_ROUTE) }
    )

    data object BookmarkOption : BottomNavOptions(
        route = BOOKMARK_ROUTE,
        labelOfIcon = "Bookmark",
        unselectedIcon = Icons.Outlined.BookmarkBorder,
        selectedIcon = Icons.Filled.Bookmark,
        onOptionClicked = { it.navigateToBottomTab(BOOKMARK_ROUTE) }
    )

    data object ProfileOption : BottomNavOptions(
        route = PROFILE_ROUTE,
        labelOfIcon = "Profile",
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        onOptionClicked = { it.navigateToBottomTab(PROFILE_ROUTE) }
    )


    companion object {
        val bottomNavOptions = listOf(
            FacultyOption,
            HistoryOption,
            BookmarkOption,
            ProfileOption
        )
    }
}

@Composable
fun CustomBottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
    hazeState: HazeState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f))
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavOptions.bottomNavOptions.forEach { option ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == option.route
                    } == true

                    BottomNavItem(
                        option = option,
                        isSelected = isSelected,
                        onClick = { option.onOptionClicked(navController) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    option: BottomNavOptions,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .wrapContentWidth(),
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isSelected) option.selectedIcon else option.unselectedIcon,
                contentDescription = option.labelOfIcon,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            AnimatedVisibility(
                visible = isSelected,
                enter = expandHorizontally(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    expandFrom = Alignment.Start
                ) + fadeIn(),
                exit = shrinkHorizontally(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    shrinkTowards = Alignment.Start
                ) + fadeOut()
            ) {
                Text(
                    text = option.labelOfIcon,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}