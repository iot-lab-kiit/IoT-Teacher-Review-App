package `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.MainActivity
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.action.ProfileActions
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.components.ProfileItemUI
import java.time.LocalDate


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
        ProfileScreen(
            navController = rememberNavController(),
            profileAction = {},
            userPhoto = "",
            userEmail = "",
            userUserName = ""
        )
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
    navController: NavController,
    profileAction: (ProfileActions) -> Unit,
    userPhoto: String,
    userEmail: String,
    userUserName : String
) {

    val context = LocalContext.current
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

            // User Profile Picture
            AsyncImage(
                model = userPhoto,
                placeholder = painterResource(id = R.drawable.profile_photo),
                contentDescription = stringResource(id = R.string.profile),
                modifier = Modifier
                    .size(72.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(8.dp))

            // User Name
            Text(
                text = userUserName ,
                style = MaterialTheme.typography.headlineSmall
            )

            // Spacing of 32 dp
            Spacer(modifier = Modifier.height(32.dp))

            // This is the Roll Number UI
            if (userEmail.contains("kiit.ac.in") == true) {
                ProfileItemUI(
                    headingTitle = R.string.roll_number,
                    leadingIcon = Icons.Default.Person,
                    fieldValue = userEmail.substringBefore("@") ?: "21051880"
                )
            }

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This is the Email ID UI
            ProfileItemUI(
                headingTitle = R.string.email_id,
                leadingIcon = Icons.Default.Person,
                fieldValue = userEmail
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            if (userEmail.contains("kiit.ac.in") == true) {
                val rollNumber = userEmail.substringBefore("@") ?: "21051880"
                val joiningYear = rollNumber.substring(0, 2).toInt()
                val currentYear = LocalDate.now().year.toString().substring(2, 4).toInt()
                val currentMonth = LocalDate.now().monthValue
                val semester = (currentYear - joiningYear) * 2 + if (currentMonth in 7..12) 1 else 0
                val finalSemester = when (semester) {
                    1 -> "1st"
                    2 -> "2nd"
                    3 -> "3rd"
                    else -> "${semester}th"
                }
                // This is Semester UI
                ProfileItemUI(
                    headingTitle = R.string.semester,
                    leadingIcon = Icons.Default.Person,
                    fieldValue = finalSemester
                )
            }

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // Sign Out Button
            Button(onClick = {
                profileAction(ProfileActions.SignOut)
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as Activity).finish()


            }) {
                Text(
                    text = "Sign Out",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}