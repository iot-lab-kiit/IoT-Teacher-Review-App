package `in`.iot.lab.teacherreview.feature_authentication.ui.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.teacherreview.R
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
        GradientButton(
            buttonShape = MaterialTheme.shapes.medium,
            buttonText = R.string.login,
            icon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock",
                    modifier = Modifier.size(24.dp)
                )

            }
        ) {}
    }
}

// This is the Gradient Button Function which gives the Gradient Button
/**
 * @param modifier Modifiers is passed to prevent Hardcoding and can be used in multiple occasion
 * @param buttonShape Its the Shape of the Button sent by the caller func
 * @param buttonText It is the Text That will be shown to the Button
 * @param onClickEvent The onClick function execution is done in the Caller Function
 */
@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    buttonShape: CornerBasedShape,
    @StringRes buttonText: Int,
    icon: @Composable (() -> Unit)? = null,
    onClickEvent: () -> Unit,
) {

    // Gradient Color List for making the Gradient
    //val gradientColors = listOf(Color(0xFF484BF1), Color(0xFF6129C5))

    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimaryContainer
    )

    // This is the Button inside which we keep the Gradient BOX implementation
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 42.dp, end = 42.dp),
        onClick = onClickEvent,
        shape = buttonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues()
    ) {

        // This Box is used to give the Gradient Color to the Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = buttonShape
                )
                .clip(buttonShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = buttonText),
                    style = MaterialTheme.typography.headlineSmall
                )
                if (icon != null) {
                    icon()
                }
            }
        }
    }
}