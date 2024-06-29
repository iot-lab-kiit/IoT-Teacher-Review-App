package `in`.iot.lab.profile.view.screens

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TertiaryButton
import `in`.iot.lab.design.state.HandleUiState
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.profile.view.components.CustomDeleteDialog
import `in`.iot.lab.profile.view.components.ProfileItemUI
import `in`.iot.lab.profile.view.event.ProfileEvents
import `in`.iot.lab.profile.view.util.findSemester
import `in`.iot.lab.kritique.domain.models.user.RemoteUser


// Preview Function
@RequiresApi(Build.VERSION_CODES.O)
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
                    uid = "",
                    name = "Anirban Basak",
                    email = "21051880@kiit.ac.in",
                    anonymousName = "",
                    photoUrl = ""
                )
            ) { }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreenControl(
    userApiState: UiState<RemoteUser>,
    logOutState: UiState<Unit>,
    setEvent: (ProfileEvents) -> Unit,
    onLogOutClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(ProfileEvents.FetchUserData)
    }

    AppScreen {

        userApiState.HandleUiState(
            onCancel = {

            },
            onTryAgain = { setEvent(ProfileEvents.FetchUserData) }
        ) {
            ProfileSuccessScreen(
                user = it,
                setEvent = setEvent
            )
        }


        logOutState.HandleUiState(
            onCancel = {
                setEvent(ProfileEvents.FetchUserData)
            },
            onTryAgain = { setEvent(ProfileEvents.ResetLogOutState) }
        ) {
            onLogOutClick()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileSuccessScreen(
    user: RemoteUser,
    setEvent: (ProfileEvents) -> Unit
) {

    var deletePress by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // User Profile Picture
            AppNetworkImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp),
                errorImage = painterResource(id = R.drawable.person),
                model = user.photoUrl,
                contentDescription = "Profile Photo"
            )


            // User Name
            Text(
                text = user.name ?: "Name Not Found",
                style = MaterialTheme.typography.titleLarge
            )

            val rollNumber = if (user.email != null && user.email!!.contains("kiit.ac.in"))
                user.email!!.substringBefore("@")
            else
                null

            ProfileItemUI(
                title = "Email ID",
                leadingIcon = Icons.Default.Email,
                description = user.email ?: "Not Found"
            )

            ProfileItemUI(
                title = "Roll Number",
                leadingIcon = Icons.Default.Numbers,
                description = rollNumber ?: "Not Found"
            )

            ProfileItemUI(
                title = "Semester",
                description = findSemester(rollNumber) ?: "Not Found",
                leadingIcon = Icons.Default.School
            )
        }


        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            // Credit Text
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Coded with ❤️ and ☕ by IoT Lab",
                textAlign = TextAlign.Center
            )

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { setEvent(ProfileEvents.SignOutEvent) }
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Log Out",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            TertiaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { deletePress = true }
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Delete Account",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }


    CustomDeleteDialog(
        deletePress = deletePress,
        onDismiss = { deletePress = false },
        onConfirm = {
            deletePress = false
            setEvent(ProfileEvents.DeleteAccountEvent)
        }
    )
}