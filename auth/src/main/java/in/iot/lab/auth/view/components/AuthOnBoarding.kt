package `in`.iot.lab.auth.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.animations.OnBoardingAnimation
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
        AppScreen {
            AuthOnBoarding()
        }
    }
}


/**
 * This function is the OnBoarding UI above the Login with Google Button.
 *
 * @param modifier The modifier to be applied to this composable.
 */
@Composable
fun AuthOnBoarding(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title Text
        Text(
            text = "Find the right college professor for you",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
            ),
            textAlign = TextAlign.Center
        )

        // Caption Text
        Text(
            text = "Explore and share your experiences with teachers at our college to help " +
                    "others make informed decisions.",
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 18.sp,
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
        )

        // OnBoarding Animation
        OnBoardingAnimation()
    }
}