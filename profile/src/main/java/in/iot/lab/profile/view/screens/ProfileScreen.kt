package `in`.iot.lab.profile.view.screens

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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.profile.view.components.ProfileItemUI
import `in`.iot.lab.profile.view.components.TopBar
import `in`.iot.lab.profile.view.event.ProfileEvents
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser


@Composable
fun ProfileScreenControl(
    userApiState: UiState<RemoteUser>,
    setEvent: (ProfileEvents) -> Unit
) {

    AppScreen(topBar = { TopBar() }) {

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
                        TODO()
                    },
                    onTryAgain = {
                        setEvent(ProfileEvents.FetchUserData)
                    }
                )
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
            .padding(32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

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
                onClick = { setEvent(ProfileEvents.SignOutEvent) }
            ) {
                Text(
                    text = "Log Out",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { setEvent(ProfileEvents.DeleteAccountEvent) }
            ) {
                Text(
                    text = "Delete Account",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}