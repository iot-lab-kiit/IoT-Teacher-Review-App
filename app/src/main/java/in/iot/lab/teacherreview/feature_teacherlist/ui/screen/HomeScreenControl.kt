package `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.FacultiesData
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.TeacherListCardItem
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.navigation.TeacherListRoutes
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.state_action.TeacherListAction
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder.TeacherListViewModel
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        HomeScreenLoading()
    }
}

// This is the Preview function of the Screen when Success
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewSuccess() {
    CustomAppTheme {
        HomeScreenSuccess(
            navController = rememberNavController(),
            teacherList = FacultiesData(),
            action = {}
        )
    }
}

// This is the Preview function of the Screen when Failure
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewFailure() {
    CustomAppTheme {
        HomeScreenFailure(
            getTeacherList = {}
        )
    }
}

/**
 * The Main Home Screen of this File which calls all the Other Composable functions and places them
 *
 * @param navController This is the NavController Object which is used to navigate Screens
 * @param myViewModel ViewModel of the [TeacherListViewModel] Class passed from
 * the navGraph
 */
@Composable
fun HomeScreenControl(
    navController: NavController,
    action : (TeacherListAction) -> Unit,
    teacherListApiCallState : TeacherListApiCallState
) {

    // Checking which State to Show
    when (teacherListApiCallState) {
        is TeacherListApiCallState.Initialized -> {
            action(TeacherListAction.GetTeacherList)
        }
        is TeacherListApiCallState.Loading -> HomeScreenLoading()
        is TeacherListApiCallState.Success -> HomeScreenSuccess(
            navController = navController,
            teacherList = teacherListApiCallState.facultyData,
            action = action
        )
        else -> HomeScreenFailure(getTeacherList = { action(TeacherListAction.GetTeacherList)})
    }
}

// This state is shown when the Screen is Loading
@Composable
fun HomeScreenLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

// This Screen is shown when the Request is a Success
@Composable
fun HomeScreenSuccess(
    modifier: Modifier = Modifier,
    navController: NavController,
    teacherList: FacultiesData,
    action: (TeacherListAction) -> Unit
) {

    Column(
        modifier = modifier,
    ) {
        // Checking if there is any data inside the Teacher List yet or not
        if (teacherList.individualFacultyData?.size != null) {
            LazyColumn {
                items(teacherList.individualFacultyData.size) {

                    // Current Teacher Detail
                    val teacher = teacherList.individualFacultyData[it]

                    // This function draws each Teacher Card
                    TeacherListCardItem(
                        navController = navController,
                        teacher = teacher
                    ) {
                        // Setting the Current Selected Teacher in the shared ViewModel
                        action(TeacherListAction.AddTeacherForNextScreen(teacher))

                        // Fetching the Teacher Reviews
                        action(TeacherListAction.GetIndividualTeacherReviews(teacher._id))

                        // Navigating to the next Screen
                        navController.navigate(TeacherListRoutes.IndividualTeacherRoute.route)
                    }
                }
            }
        }
    }
}

// This Screen is Shown when the Request is a Failure
@Composable
fun HomeScreenFailure(
    modifier: Modifier = Modifier,
    getTeacherList: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = getTeacherList
        ) {
            Text(
                text = stringResource(R.string.failed_to_load_tap_to_retry),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}