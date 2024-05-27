package `in`.iot.lab.review.view.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import `in`.iot.lab.review.view.screens.ReviewScreenControl
import `in`.iot.lab.review.vm.FacultyViewModel


const val REVIEW_ROUTE = "review-root-route"

fun NavGraphBuilder.reviewNavGraph(navController: NavController) {

    composable(REVIEW_ROUTE) {

        val viewModel: FacultyViewModel = hiltViewModel()
        val facultyList = viewModel.facultyList.collectAsState().value

        ReviewScreenControl(
            facultyListState = facultyList,
            setEvent = viewModel::uiListener
        )
    }
}