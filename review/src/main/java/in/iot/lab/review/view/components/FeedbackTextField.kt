package `in`.iot.lab.review.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.GlassBorder
import `in`.iot.lab.design.theme.primaryColor
import `in`.iot.lab.design.theme.surfaceVariantColor


private const val CHARACTER_LIMIT = 500

@Composable
fun FeedbackTextField(
    input: String,
    onInputChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = input,
        onValueChange = {
            if (it.length <= CHARACTER_LIMIT) onInputChanged(it)
        },
        placeholder = {
            Text(
                text = "Describe your experience",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        minLines = 4,
        maxLines = 4,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor.copy(alpha = 0.7f),
            unfocusedBorderColor = GlassBorder.copy(alpha = 0.5f),
            focusedContainerColor = surfaceVariantColor.copy(alpha = 0.4f),
            unfocusedContainerColor = surfaceVariantColor.copy(alpha = 0.25f),
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = primaryColor
        ),
        supportingText = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "${input.length}/$CHARACTER_LIMIT",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
    )
}