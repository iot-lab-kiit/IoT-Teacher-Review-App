package `in`.iot.lab.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import `in`.iot.lab.design.R

@Preview
@Composable
private fun Preview() {
    Loading()
}

/**
 * This composable is used to show the loading animation in the app.
 */

@Composable
fun Loading() {
    val compositionTick by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = compositionTick,
                modifier = Modifier.size(150.dp),
                iterations = LottieConstants.IterateForever
            )
        }

    }
}