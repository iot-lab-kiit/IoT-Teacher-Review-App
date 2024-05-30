package `in`.iot.lab.design.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import `in`.iot.lab.design.R

/**
 * This composable function is used to show the delete animation when any review is deleted.
 */

@Composable
fun ReviewDeletedAnim() {
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

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(CircleShape),
            contentAlignment = Alignment.Center){
            LottieAnimation(
                composition = compositionTick,
                modifier = Modifier.size(150.dp)
            )
        }
        Text(text = "REVIEW DELETED",
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = textAlpha.value),
            fontSize = (24.sp * textScale.value))
    }
}
