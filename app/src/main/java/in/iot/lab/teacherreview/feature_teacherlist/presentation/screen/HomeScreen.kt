package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.TeacherListCardItem
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.TeacherListViewModel

// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        HomeScreen(navController = rememberNavController())
    }
}

/**
 * The Main Register Screen of this File which calls all the Other Composable functions and places them
 *
 * @param navController This is the NavController Object which is used to navigate Screens
 * @param modifier  Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    // ViewModel Variable
    val myViewModel: TeacherListViewModel = viewModel()


    Column(
        modifier = modifier
    ) {

        // Checking if there is any data inside the Teacher List yet or not
        if (myViewModel.teacherList?.individualFacultyData?.size != null) {
            LazyColumn {
                items(myViewModel.teacherList!!.individualFacultyData!!.size) {

                    // Current Teacher Detail
                    val teacher = myViewModel.teacherList!!.individualFacultyData?.get(it)

                    // This function draws each Teacher Card
                    TeacherListCardItem(
                        navController = navController,
                        teacherName = teacher!!.name,

                        // TODO :-- Need to Add the Subject here but first at the Server
                        subjectTaught = teacher._id
                    ) {
                        // TODO ------------
                    }
                }
            }
        }
    }
}