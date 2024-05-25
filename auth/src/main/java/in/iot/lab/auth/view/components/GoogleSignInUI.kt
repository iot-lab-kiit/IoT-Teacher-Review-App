package `in`.iot.lab.auth.view.components


import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import `in`.iot.lab.auth.R
import `in`.iot.lab.design.theme.CustomAppTheme


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
        GoogleSignInUI(
            onAuthCredentialFound = {},
            onExceptionFound = {}
        )
    }
}


/**
 * This function is used to show the Facebook Sign In Button UI.
 *
 * @param modifier This is the default parameter for passing modifications from the parent function.
 * @param onAuthCredentialFound This is the function which is used to send back the auth credential
 * found from the google provider back to the Repo layer for Auth flow and signing the user.
 * @param onExceptionFound This is the exception found if any during showing the Intent or fetching
 * the Auth Credentials.
 */
@Composable
fun GoogleSignInUI(
    modifier: Modifier = Modifier,
    onAuthCredentialFound: (AuthCredential) -> Unit,
    onExceptionFound: (Exception) -> Unit
) {

    val context = LocalContext.current

    // Google Sign in web Client ID
    val googleClientId = stringResource(id = R.string.web_client_id)

    // Launcher to show the Sign in Intent to the User.
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {

            // Checking ig the Result code is OK
            if (it.resultCode == Activity.RESULT_OK) {
                try {

                    // Fetching the Token
                    val idToken = GoogleSignIn
                        .getSignedInAccountFromIntent(it.data)
                        .getResult(ApiException::class.java)
                        .idToken

                    // Fetching the Auth Credentials from the Google Auth Provider
                    val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)
                    onAuthCredentialFound(googleCredentials)
                } catch (e: Exception) {
                    onExceptionFound(e)
                }
            } else
                onExceptionFound(Exception("Activity Cancelled by the User"))
        }
    )

    // This function is executed when the sign in button is clicked
    val signInClick: () -> Unit = {

        // Creating the Google Sign In Options
        val options = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(googleClientId)
            .requestId()
            .requestProfile()
            .build()

        // Creating the intent for the Launcher
        val client = GoogleSignIn.getClient(context, options)
        client.signOut()

        // Starting the Launcher
        signInLauncher.launch(client.signInIntent)
    }

    // Sign in with google button
    ElevatedButton(
        onClick = signInClick,
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        contentPadding = PaddingValues(15.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)
    )  {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "Login With Google",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp
                )
            )
        }
    }
}