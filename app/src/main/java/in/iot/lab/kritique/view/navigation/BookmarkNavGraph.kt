package `in`.iot.lab.kritique.view.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.kritique.view.screens.BookmarkScreen

const val BOOKMARK_ROUTE = "bookmark_route"

fun NavGraphBuilder.bookmarkNavGraph(onExploreClick: () -> Unit) {
    composable(BOOKMARK_ROUTE) {
        BookmarkScreen(onExploreClick = onExploreClick)
    }
}

fun NavController.navigateToBookmark() {
    navigate(BOOKMARK_ROUTE)
}
