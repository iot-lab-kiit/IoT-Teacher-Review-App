package `in`.iot.lab.kritique.view.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import `in`.iot.lab.kritique.view.screens.BookmarkScreen

const val BOOKMARK_ROUTE = "bookmark_route"

fun NavGraphBuilder.bookmarkNavGraph(
    onExploreClick: () -> Unit,
    onFacultyClick: (String) -> Unit
) {
    composable(BOOKMARK_ROUTE) {
        BookmarkScreen(
            onExploreClick = onExploreClick,
            onFacultyClick = onFacultyClick
        )
    }
}