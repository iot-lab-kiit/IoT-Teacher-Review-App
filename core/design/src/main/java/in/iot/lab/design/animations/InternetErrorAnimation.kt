package `in`.iot.lab.design.animations

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import `in`.iot.lab.design.R
import `in`.iot.lab.design.theme.CustomAppTheme
import kotlinx.coroutines.delay

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview3() {
    CustomAppTheme {
        InternetErrorAnimation()
    }
}

/**
 * This composable function is used to show an internet error screen with an animation.
 */
@Composable
fun InternetErrorAnimation(
    modifier: Modifier = Modifier,
    message: String = "No internet connection. Please try again later.",
    onAnimationComplete: (() -> Unit)? = null
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.internet_error))
    val progress by animateLottieCompositionAsState(composition)

    var isVisible by remember { mutableStateOf(true) }

    onAnimationComplete?.let {
        LaunchedEffect(progress) {
            if (progress == 1f) {
                delay(1000)
                isVisible = false
                it()
            }
        }
    }

    AnimationDialog(
        modifier = modifier,
        isVisible = isVisible
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(200.dp)
        )

        AnimatedVisibility(
            visible = (progress == 1f),
            enter = fadeIn() + expandVertically()
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
