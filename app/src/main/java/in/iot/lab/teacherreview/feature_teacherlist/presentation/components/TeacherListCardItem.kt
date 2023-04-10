package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme

// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        TeacherListCardItem(
            navController = rememberNavController(),
            teacherName = "Anirban Basak",
            subjectTaught = "Operating System"
        ) {}
    }
}

/**
 * The Main Register Screen of this File which calls all the Other Composable functions and places them
 *
 * @param navController This is the NavController Object which is used to navigate Screens
 * @param modifier  Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 */
@Composable
fun TeacherListCardItem(
    modifier: Modifier = Modifier,
    navController: NavController,
    teacherName: String,
    subjectTaught: String,
    onTeacherClick: () -> Unit
) {

    // Outer Card shown for each Teacher
    Card(
        modifier = modifier
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable {
                onTeacherClick()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // This is the profile photo of the Teacher
            Image(
                painter = painterResource(id = R.drawable.profile_photo),
                contentDescription = stringResource(id = R.string.profile),
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            // This Column displays the Details of the Teacher
            Column(
                modifier = Modifier
                    .padding(start = 24.dp)
            ) {

                // Name of the Teacher
                Text(
                    text = teacherName,
                    style = MaterialTheme.typography.titleMedium
                )

                // Subject Taught by the teacher
                Text(
                    text = subjectTaught,
                    style = MaterialTheme.typography.bodySmall
                )

                // This function draws the stars according to the rating
                StarsRow(starCount = 3.9)
            }

            // This Box Contains the Show More Button to the end of every Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                // Arrow Forward Button
                Image(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(R.string.show_more_details),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }
}