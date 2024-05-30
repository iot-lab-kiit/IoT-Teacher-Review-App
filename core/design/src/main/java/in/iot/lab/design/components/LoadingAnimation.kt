package `in`.iot.lab.design.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
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
            Loading()
        }
    }
}


/**
 * This composable is used to show the loading animation in the app.
 */
@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    val compositionTick by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = compositionTick,
            modifier = Modifier.size(150.dp),
            iterations = LottieConstants.IterateForever
        )
    }
}