package `in`.iot.lab.design.components

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import `in`.iot.lab.design.R
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
            ReviewDeletedAnim()
        }
    }
}


/**
 * This composable function is used to show the delete animation when any review is deleted.
 */
@Composable
fun ReviewDeletedAnim(
    modifier: Modifier = Modifier
) {

    val compositionTick by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.delete_anim))

    val textAlpha = remember { Animatable(0f) }
    val textScale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, delayMillis = 0)
        )
        textScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, delayMillis = 0)
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = compositionTick,
                modifier = Modifier.size(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Review Deleted",
            style = MaterialTheme.typography.titleLarge
        )
    }
}
