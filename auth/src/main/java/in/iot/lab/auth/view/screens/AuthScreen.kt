package `in`.iot.lab.auth.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.auth.view.components.AuthOnBoarding
import `in`.iot.lab.auth.view.components.GoogleSignInUI
import `in`.iot.lab.auth.view.events.AuthEvent
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.network.state.UiState


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
        Surface {
            AuthIdleScreen {}
        }
    }
}


@Composable
fun AuthScreenControl(
    authApiState: UiState<Unit>,
    setEvent: (AuthEvent) -> Unit,
    onSignInSuccess: () -> Unit
) {

    Scaffold {
        Box(modifier = Modifier.padding(it)) {

            AuthIdleScreen(setEvent)


            when (authApiState) {

                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    onSignInSuccess()
                }

                is UiState.Failed -> {
                    TODO(" Failed Dialog Box Needs to be added")
                }

                else -> {
                    // Do Nothing
                }
            }
        }
    }
}


@Composable
fun AuthIdleScreen(setEvent: (AuthEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        AuthOnBoarding()
        Spacer(modifier = Modifier.height(32.dp))
        GoogleSignInUI(
            onAuthCredentialFound = {
                setEvent(AuthEvent.LoginUser(it))
            }
        ) {
            setEvent(AuthEvent.ExceptionFound(it))
        }
    }
}