package `in`.iot.lab.profile.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.profile.view.components.ProfileItemUI
import `in`.iot.lab.profile.view.event.ProfileEvents
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    CustomAppTheme {
        ProfileSuccessScreen(
            user = RemoteUser(
                id = "",
                uid = "",
                name = "Anirban Basak",
                email = "21051880@kiit.ac.in",
                photoUrl = "",
                role = "User",
                status = false
            )
        ) { }
    }
}


@Composable
fun ProfileScreenControl(
    userApiState: UiState<RemoteUser>,
    logOutState: UiState<Unit>,
    deleteAccountState: UiState<Unit>,
    setEvent: (ProfileEvents) -> Unit,
    onLogOutClick: () -> Unit
) {

    AppScreen {

        when (userApiState) {

            is UiState.Idle -> {
                setEvent(ProfileEvents.FetchUserData)
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                ProfileSuccessScreen(
                    user = userApiState.data,
                    setEvent = setEvent
                )
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = userApiState.message,
                    onCancel = {

                    },
                    onTryAgain = {
                        setEvent(ProfileEvents.FetchUserData)
                    }
                )
            }
        }

        when (logOutState) {
            is UiState.Success -> {
                onLogOutClick()
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            else -> {}
        }

        when (deleteAccountState) {
            is UiState.Success -> {
                onLogOutClick()
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = deleteAccountState.message,
                    onCancel = {

                    },
                    onTryAgain = {
                        setEvent(ProfileEvents.FetchUserData)
                    }
                )
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            else -> {

            }
        }
    }
}


@Composable
fun ProfileSuccessScreen(
    user: RemoteUser,
    setEvent: (ProfileEvents) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge
            )

            // User Profile Picture
            AsyncImage(
                model = user.photoUrl,
//                placeholder = painterResource(id = R.drawable.profile_photo),
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .size(72.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit
            )


            // User Name
            Text(
                text = user.name ?: "Name Not Found",
                style = MaterialTheme.typography.titleMedium
            )

            ProfileItemUI(
                headingTitle = "Roll Number",
                leadingIcon = Icons.Default.Person,
                fieldValue = user.email?.substringBefore("@") ?: "Not Found"
            )

            // This is the Email ID UI
            ProfileItemUI(
                headingTitle = "Email Id",
                leadingIcon = Icons.Default.Person,
                fieldValue = user.email ?: "Not Found"
            )

            // This is Semester UI
            ProfileItemUI(
                headingTitle = "Semester",
                leadingIcon = Icons.Default.Person,
                fieldValue = "Semester"
            )
        }


        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { setEvent(ProfileEvents.SignOutEvent) },
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Log Out",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { setEvent(ProfileEvents.DeleteAccountEvent) },
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Delete Account",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}