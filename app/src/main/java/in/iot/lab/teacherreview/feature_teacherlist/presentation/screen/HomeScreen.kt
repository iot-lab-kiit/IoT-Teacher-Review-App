package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.TeacherListCardItem

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
    
    Column(
        modifier = modifier
    ) {

        LazyColumn {
            items(5) {
                TeacherListCardItem(
                    navController = navController,
                    teacherName = "Anirban Basak",
                    subjectTaught = "Operating System"
                ) {
                    // TODO ------------
                }
            }
        }
    }
}