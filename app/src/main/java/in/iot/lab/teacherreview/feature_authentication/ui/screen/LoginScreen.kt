package `in`.iot.lab.teacherreview.feature_authentication.ui.screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.feature_authentication.ui.components.LoginButton
import `in`.iot.lab.teacherreview.feature_authentication.ui.components.ScreenHeader

@Preview("Light")
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        LoginScreenImpl(
            isLoading = true
        )
    }
}


@Composable
fun LoginScreen(
    onUserSignedIn: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        account.idToken?.let { idToken ->
                            // Initiate sign in
                            viewModel.signIn(idToken)
                        }
                    } else {
                        Toast.makeText(context, "Failed to sign in", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: ApiException) {
                    Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, "Sign-in cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "Sign-in failed with error code ${result.resultCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (viewModel.checkIfUserIsLoggedIn()) {
            onUserSignedIn()
        }
    }


    when (state) {
        is LoginState.Success -> {
            loading = false
            onUserSignedIn()
        }

        is LoginState.Loading -> {
            loading = true
        }

        is LoginState.Failure -> {
            loading = false
            Toast.makeText(
                context,
                (state as LoginState.Failure).message,
                Toast.LENGTH_SHORT
            ).show()
        }
        is LoginState.Initialized -> {
            loading = false
        }
    }

    LoginScreenImpl(
        isLoading = loading,
        onSignIn = {
            loading = true
            launcher.launch(viewModel.getSignInIntent())
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenImpl(
    isLoading: Boolean = false,
    onSignIn: () -> Unit = {}
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            ScreenHeader()
            Spacer(modifier = Modifier.height(32.dp))
            LoginButton(
                isLoading = isLoading,
                onClick = onSignIn,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}