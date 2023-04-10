package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.ProfileItemUI

// This is the Preview function of the Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        ProfileScreen(navController = rememberNavController())
    }
}

/**
 * This is the Profile Screen Main UI Function
 *
 * @param modifier Default Modifier to pass modifications from the parent function
 * @param navController This is the navigation Controller which helps in navigation
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    // This is the Parent Composable which contains all the Components
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        // This is the Composable which contains the Card and the Add Rating Heading
        Column(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, top = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Add Review Header Text
            Text(
                text = stringResource(R.string.profile),
                style = MaterialTheme.typography.headlineSmall,
            )

            // Spacing of 32 dp
            Spacer(modifier = Modifier.height(32.dp))

            // Teacher Profile Picture
            Image(
                painter = painterResource(id = R.drawable.profile_photo),
                contentDescription = stringResource(id = R.string.profile),
                modifier = Modifier
                    .size(72.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(8.dp))

            // User Name
            // TODO Need to attach the Real Name of the User
            Text(
                text = "Anirban Basak",
                style = MaterialTheme.typography.headlineSmall
            )

            // Spacing of 32 dp
            Spacer(modifier = Modifier.height(32.dp))

            // This is the Roll Number UI
            ProfileItemUI(
                headingTitle = R.string.roll_number,
                leadingIcon = Icons.Default.Person,
                fieldValue = "21051880"
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This is the Email ID UI
            ProfileItemUI(
                headingTitle = R.string.email_id,
                leadingIcon = Icons.Default.Person,
                fieldValue = "21051880@kiit.ac.in"
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This is the Semester UI
            ProfileItemUI(
                headingTitle = R.string.semester,
                leadingIcon = Icons.Default.Person,
                fieldValue = "Third"
            )

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // Sign Out Button
            Button(onClick = {
                /*TODO*/
            }) {
                Text(
                    text = "Sign Out",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}