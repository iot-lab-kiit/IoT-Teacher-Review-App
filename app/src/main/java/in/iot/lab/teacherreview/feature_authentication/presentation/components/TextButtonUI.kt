package `in`.iot.lab.teacherreview.feature_authentication.presentation.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
        TextButtonUI(textToShow = R.string.app_name) {}
    }
}

// This Function creates a TextButton
/**
 * @param modifier  Modifiers is passed to prevent Hardcoding and can be used in multiple occasion
 * @param textToShow It is the Text that needs to be shown
 * @param onClickEvent It is the Event which occurs when the User taps on the Text
 */
@Composable
fun TextButtonUI(
    modifier: Modifier = Modifier,
    @StringRes textToShow: Int,
    onClickEvent: () -> Unit
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClickEvent
    ) {
        Text(
            text = stringResource(id = textToShow),
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge
        )
    }
}