package `in`.iot.lab.profile.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.animations.AmongUsAnimation
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TertiaryButton
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
        AppScreen {
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
}


@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview2() {
    CustomAppTheme {
        AppScreen {
            ProfileScreenControl(
                userApiState = UiState.Failed("Can't Fetch user data due to Firebase Issue."),
                logOutState = UiState.Idle,
                setEvent = {},
                onLogOutClick = {}
            )
        }
    }
}


@Composable
fun ProfileScreenControl(
    userApiState: UiState<RemoteUser>,
    logOutState: UiState<Unit>,
    setEvent: (ProfileEvents) -> Unit,
    onLogOutClick: () -> Unit
) {

    AppScreen {

        when (userApiState) {

            is UiState.Idle -> {
                setEvent(ProfileEvents.FetchUserData)
            }

            is UiState.Loading -> {
                AmongUsAnimation()
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
                AmongUsAnimation()
            }

            else -> {}
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

            // User Profile Picture
            AppNetworkImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp),
                model = user.photoUrl,
                contentDescription = "Profile Photo"
            )


            // User Name
            Text(
                text = user.name ?: "Name Not Found",
                style = MaterialTheme.typography.titleLarge
            )

            listOf(
                Pair("Roll Number", user.email?.substringBefore("@")),
                Pair("Email Id", user.email),
                Pair("Semester", null)
            ).forEach {
                ProfileItemUI(
                    title = it.first,
                    leadingIcon = Icons.Default.Person,
                    description = it.second ?: "Not Found"
                )
            }
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

            TertiaryButton(
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