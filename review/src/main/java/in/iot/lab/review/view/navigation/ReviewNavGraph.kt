package `in`.iot.lab.review.view.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.review.view.screens.ReviewScreenControl


const val REVIEW_ROUTE = "review-root-route"

fun NavGraphBuilder.reviewNavGraph(navController: NavController) {

    composable(REVIEW_ROUTE) {
        ReviewScreenControl()
    }
}