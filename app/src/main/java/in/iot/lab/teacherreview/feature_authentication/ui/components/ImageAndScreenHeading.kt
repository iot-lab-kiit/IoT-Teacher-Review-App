package `in`.iot.lab.teacherreview.feature_authentication.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
        ImageAndScreenHeading(
            image = R.drawable.image_login_screen,
            screenHeader = R.string.app_name
        )
    }
}

// This function draws a Image and a Heading for the Screen under the Image
/**
 * @param modifier Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 * @param image Drawable Resource of the Image which needs to be Displayed
 * @Param screenHeader Header of the Screen (Login Screen , Sign up Screen , Register Screen)
 * @param contentDescription Content Description for the Image
 */
@Composable
fun ImageAndScreenHeading(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    @StringRes screenHeader: Int,
    contentDescription: String? = null
) {

    Column {
        //Image which will be Drawn in the UI
        Image(
            painter = painterResource(id = image),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .height(180.dp)
                .fillMaxWidth(),
        )

        // Space between the Image and the Heading Text Composable
        Spacer(modifier = Modifier.height(4.dp))

        // This is the Heading of the Screen which will be Shown under the Image
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = screenHeader),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}
