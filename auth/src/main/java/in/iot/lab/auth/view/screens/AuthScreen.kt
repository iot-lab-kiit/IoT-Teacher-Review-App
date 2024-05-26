package `in`.iot.lab.auth.view.screens

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.auth.view.components.AuthOnBoarding
import `in`.iot.lab.auth.view.components.GoogleLoginButton
import `in`.iot.lab.auth.view.events.AuthEvent
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
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

    val context = LocalContext.current

    AppScreen {
        AuthIdleScreen(setEvent)


        when (authApiState) {

            // Loading State
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            // Success State
            is UiState.Success -> {
                onSignInSuccess()
            }

            // failed State
            is UiState.Failed -> {
                AppFailureScreen(
                    text = authApiState.message,
                    onCancel = { (context as Activity).finish() },
                    onTryAgain = { setEvent(AuthEvent.ResetAuthApiState) }
                )
            }

            // Idle State
            else -> {
                // Do Nothing
            }
        }
    }
}


@Composable
fun AuthIdleScreen(setEvent: (AuthEvent) -> Unit) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // On Boarding Texts and Image
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            AuthOnBoarding()
        }

        // Log In Option
        GoogleLoginButton(
            onAuthCredentialFound = {
                setEvent(AuthEvent.LoginUser(it))
            },
            onExceptionFound = {
                setEvent(AuthEvent.ExceptionFound(it))
            }
        )
    }
}