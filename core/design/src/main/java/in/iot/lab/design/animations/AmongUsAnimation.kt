package `in`.iot.lab.design.animations

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppScreen
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
            AmongUsAnimation()
        }
    }
}


/**
 * This composable is used to show the loading animation in the app.
 */
@Composable
fun AmongUsAnimation(
    modifier: Modifier = Modifier,
    onAnimationComplete: (() -> Unit)? = null
) {

    // Animation Composition
    val composition1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.among_us_primary_animation))
    val composition2 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.among_us_secondary_animation))
    val composition3 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.among_us_tertiary_animation))

    val progress1 by animateLottieCompositionAsState(composition1)
    val progress2 by animateLottieCompositionAsState(composition2)
    val progress3 by animateLottieCompositionAsState(composition3)

    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Box(modifier = Modifier.padding(end = 120.dp)) {
            LottieAnimation(
                modifier = modifier.size(120.dp),
                composition = composition1,
                iterations = LottieConstants.IterateForever,
                clipToCompositionBounds = false
            )
        }

        Box {
            LottieAnimation(
                modifier = modifier.size(120.dp),
                composition = composition2,
                iterations = LottieConstants.IterateForever
            )
        }

        Box(modifier = Modifier.padding(start = 120.dp)) {
            LottieAnimation(
                modifier = modifier.size(120.dp),
                composition = composition3,
                iterations = LottieConstants.IterateForever
            )
        }
    }

    onAnimationComplete?.let {
        if (progress1 == 1.0f && progress2 == 1.0f && progress3 == 1.0f) it()
    }
}