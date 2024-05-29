package `in`.iot.lab.review.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.review.view.screens.PostReviewScreenControl
import `in`.iot.lab.review.view.screens.ReviewDetailScreenControl
import `in`.iot.lab.review.view.screens.FacultyListScreenControl
import `in`.iot.lab.review.vm.FacultyViewModel


const val FACULTY_ROOT_ROUTE = "review-root-route"
internal const val FACULTY_LIST_ROUTE = "teacher-list-route"
internal const val FACULTY_DETAIL_ROUTE = "teacher-detail-route"
internal const val REVIEW_POST_ROUTE = "review-post-route"


@Composable
fun FacultyNavGraph(
    navController: NavHostController = rememberNavController()
) {

    // View Model
    val viewModel: FacultyViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = FACULTY_LIST_ROUTE
    ) {

        // Teacher List Route
        composable(FACULTY_LIST_ROUTE) {
            val facultyList = viewModel.facultyList.collectAsState().value

            FacultyListScreenControl(
                facultyListState = facultyList,
                setEvent = viewModel::uiListener,
                navigator = navController::navigate
            )
        }

        // Teacher Review Detail screen
        composable(FACULTY_DETAIL_ROUTE) {

            val facultyData = viewModel.facultyDetails.collectAsState().value

            ReviewDetailScreenControl(
                faculty = facultyData,
                setEvent = viewModel::uiListener,
                navigator = navController::navigate
            )
        }

        // Review Post Screen
        composable(REVIEW_POST_ROUTE) {
            PostReviewScreenControl(
                setEvent = viewModel::uiListener,
                onDiscardClick = navController::popBackStack
            )
        }
    }
}