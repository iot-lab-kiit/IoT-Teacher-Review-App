package `in`.iot.lab.teacherreview.feature_authentication.presentation.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme

// Preview Function For Both Light and Dark Mode of the App
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        UserInputUI(
            inputFieldLabel = R.string.collapsed,
            userInput = "",
            keyboardActions = KeyboardActions(),
            keyboardOptions = KeyboardOptions()
        ) {}
    }
}

// This Shows a OutLinedTextField and takes input from user
/**
 * @param modifier Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 * @param userInput Input which will be given by the User
 * @param inputFieldLabel Label which will be shown in the OutlinedTextField
 * @param keyboardActions Keyboard Action which will be available to the User
 * @param keyboardOptions Keyboard Options to define the User input Type
 * @param visualTransformation To show the Typed things or hide them by *
 * @param trailingIcon To set a Trailing Icon to the Input Text Field
 * @param onTextChange Function which is passed to the Function to update the state in caller func
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputUI(
    modifier: Modifier = Modifier,
    userInput: String,
    @StringRes inputFieldLabel: Int,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTextChange: (String) -> Unit
) {

    // Outlined Text Field for the User to input his data regarding email or Phone Number
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        value = userInput,
        onValueChange = onTextChange,

        // Label to be shown to the User
        label = {
            Text(
                text = stringResource(id = inputFieldLabel),
                style = MaterialTheme.typography.labelMedium,
            )
        },

        // Shape of the TextField
        shape = MaterialTheme.shapes.medium,
        placeholder = {
            Text(text = stringResource(id = inputFieldLabel))
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon ,
        singleLine = true
    )
}