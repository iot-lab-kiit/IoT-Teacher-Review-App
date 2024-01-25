package `in`.iot.lab.teacherreview.feature_authentication.presentation.screen

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
        LoginScreen()
    }
}

/**
 * The Main Register Screen of this File which calls all the Other Composable functions and places them
 *
 * @param modifier  Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    // Focus Manager for Input Text Fields
    val focusManager = LocalFocusManager.current
    // ViewModel Class Variable for Storing Data and User UI events
    val myViewModel = viewModel<LoginViewModel>()
    // Context of the Activity
    val context = LocalContext.current
    // Boolean which stores if there is already a Login Request being processed at the time
    var loginRequestEmpty = true

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
            loginRequestEmpty = true

            // Starting the New Activity
            context.startActivity(Intent(context, HomeActivity::class.java))
            (context as Activity).finish()

        }
        is LoginState.Loading -> {
            loginRequestEmpty = false
        }
        is LoginState.Failure -> {
            Toast.makeText(
                context,
                (myViewModel.loginState as LoginState.Failure).errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
        else -> {}
    }


    // Surface Covers the Whole screen and keeps the background color for Better App UI colors
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        // This Column Aligns the UI vertically one after another
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
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
                }
            ) {

                // Checking if already a login Request is getting processed
                if (loginRequestEmpty)
                    myViewModel.sendLoginRequest(context)
                else
                    Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
            }
        }
    }
}