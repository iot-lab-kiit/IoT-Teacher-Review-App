package `in`.iot.lab.teacherreview.feature_authentication.presentation.screen

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.core.theme.buttonShape
import `in`.iot.lab.teacherreview.feature_authentication.presentation.components.GradientButton
import `in`.iot.lab.teacherreview.feature_authentication.presentation.components.ImageAndScreenHeading
import `in`.iot.lab.teacherreview.feature_authentication.presentation.stateholder.LoginViewModel
import `in`.iot.lab.teacherreview.feature_authentication.util.LoginState
import `in`.iot.lab.teacherreview.feature_bottom_navigation.HomeActivity

// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        LoginScreenImpl(
            isLoading = true
        )
    }
}

// For Readability, Renamed The Launcher used for Legacy Google SignIn
internal typealias SignInLauncher = ManagedActivityResultLauncher<Intent, ActivityResult>

@Composable
fun LoginScreen() {
    val myViewModel = viewModel<LoginViewModel>()
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = myViewModel::onSignInResult
    )

    // This Effect is called when the Screen is Launched
    LaunchedEffect(Unit) {
        if (myViewModel.checkIfUserIsLoggedIn()) {
            context.startActivity(Intent(context, HomeActivity::class.java))
            (context as Activity).finish()
        }
    }


    // Checking what to do according to the different States of UI
    when (myViewModel.loginState) {
        is LoginState.Success -> {
            loading = false
            context.startActivity(Intent(context, HomeActivity::class.java))
            (context as Activity).finish()
        }

        is LoginState.Loading -> {
            loading = true
        }

        is LoginState.Failure -> {
            loading = false
            Toast.makeText(
                context,
                (myViewModel.loginState as LoginState.Failure).errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }

        else -> {}
    }

    LoginScreenImpl(
        isLoading = loading,
        onSignIn = {
            myViewModel.signIn(context, launcher)
        }
    )
}

@Composable
private fun LoginScreenImpl(
    isLoading: Boolean = false,
    onSignIn: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        // This Column Aligns the UI vertically one after another
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // This Function draws the Image and the Heading Text For the Screen
            // Passing the Image and the Company Name to be Drawn in the UI
            ImageAndScreenHeading(
                image = R.drawable.image_login_screen,
                screenHeader = R.string.app_name
            )

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This Function draws the Button taking the shape and the Text to be shown
            GradientButton(
                buttonShape = buttonShape,
                buttonText = R.string.login,
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier.size(36.dp)
                    )
                },
                onClickEvent = onSignIn
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}