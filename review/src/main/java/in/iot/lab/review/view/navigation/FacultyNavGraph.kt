package `in`.iot.lab.review.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.review.view.screens.ReviewDetailScreenControl
import `in`.iot.lab.review.view.screens.ReviewScreenControl
import `in`.iot.lab.review.vm.FacultyViewModel


const val FACULTY_ROOT_ROUTE = "review-root-route"
const val TEACHER_LIST_ROUTE = "teacher-list-route"
const val TEACHER_DETAIL_ROUTE = "teacher-detail-route"


@Composable
fun FacultyNavGraph(
    navController: NavHostController = rememberNavController()
) {

    // View Model
    val viewModel: FacultyViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = TEACHER_LIST_ROUTE
    ) {

        // Teacher List Route
        composable(TEACHER_LIST_ROUTE) {
            val facultyList = viewModel.facultyList.collectAsState().value

            ReviewScreenControl(
                facultyListState = facultyList,
                setEvent = viewModel::uiListener,
                navigator = navController::navigate
            )
        }

        // Teacher Review Detail screen
        composable(TEACHER_DETAIL_ROUTE) {
            ReviewDetailScreenControl(viewModel.selectedFaculty)
        }
    }
}