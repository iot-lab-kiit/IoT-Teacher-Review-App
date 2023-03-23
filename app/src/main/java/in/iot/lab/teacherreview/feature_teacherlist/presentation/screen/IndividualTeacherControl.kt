package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.ReviewCardItem
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.TeacherDetailsHeaderCard
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.TeacherListViewModel

// This is the Preview function of the Teacher Review Control Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewControl() {
    CustomAppTheme {
        IndividualTeacherControl(
            navController = rememberNavController(),
            TeacherListViewModel()
        )
    }
}

// This is the Preview function of the Teacher Review Loading Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        IndividualTeacherLoading(
            selectedTeacher = TeacherListViewModel().selectedTeacher!!
        )
    }
}

// This is the Preview function of the Teacher Review Success Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewSuccess() {
    CustomAppTheme {
        IndividualTeacherSuccess(
            navController = rememberNavController(),
            myViewModel = TeacherListViewModel()
        )
    }
}

// This is the Preview function of the Teacher Review Failure Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewFailure() {
    CustomAppTheme {
        IndividualTeacherFailure(
            myViewModel = TeacherListViewModel()
        )
    }
}

/**
 * Individual Teacher Control for Review Screen
 *
 * @param navController This is kept to navigate to different Screens
 * @param myViewModel This is the [TeacherListViewModel] variable
 */
@Composable
fun IndividualTeacherControl(
    navController: NavController,
    myViewModel: TeacherListViewModel
) {

    IndividualTeacherSuccess(navController = navController, myViewModel = myViewModel)

}

/**
 * This function is Used when the Screen is Loading
 *
 * @param modifier Default kept to let the parent class pass any modifications it needs
 * @param selectedTeacher This is the details of the Selected Teacher
 */
@Composable
fun IndividualTeacherLoading(
    modifier: Modifier = Modifier,
    selectedTeacher: IndividualFacultyData
) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {

        // Showing the Teacher Details
        TeacherDetailsHeaderCard(selectedTeacher = selectedTeacher)

        // Spacer of Height 16 dp
        Spacer(modifier = Modifier.height(16.dp))


        // Showing the Progress Bar
        Box(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

/**
 * This function is Used when the Screen is Success
 *
 * @param navController This is the controller used to navigate to different screens
 * @param myViewModel This is the [TeacherListViewModel] variable
 */
@Composable
fun IndividualTeacherSuccess(
    navController: NavController,
    myViewModel: TeacherListViewModel
) {

    // Lazy Column to Show the List of Reviews
    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
    ) {
        items(150) {
            val itemCount = it - 1

            // Drawing the Header of the Teacher with his overall stats
            if (itemCount == -1) {
                TeacherDetailsHeaderCard(selectedTeacher = myViewModel.selectedTeacher!!)

                // Spacer of Height 16 dp
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                ReviewCardItem(myViewModel = myViewModel)

                // Spacer of Height 16 dp
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 *  This Screen is used when the Request is a Failure
 *
 *  @param modifier Default modifier to pass modifications from the Parent
 *  @param myViewModel [TeacherListViewModel] ViewModel variable
 */
@Composable
fun IndividualTeacherFailure(
    modifier: Modifier = Modifier,
    myViewModel: TeacherListViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Drawing the Header of the Teacher with his overall stats
        TeacherDetailsHeaderCard(selectedTeacher = myViewModel.selectedTeacher!!)

        // Spacer of Height 16 dp
        Spacer(modifier = Modifier.height(16.dp))

        // This is a text Button which says to Try Again
        TextButton(
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = stringResource(R.string.failed_to_load_tap_to_retry),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}