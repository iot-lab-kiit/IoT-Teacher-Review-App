package `in`.iot.lab.design.animations

import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.TileMode

fun Modifier.shimmer(): Modifier = composed {
    val shimmerColors = listOf(
        Color(0xFF2A2A2A).copy(alpha = 0.3f),      // Much lower opacity
        Color(0xFF3D3D3D).copy(alpha = 0.5f),      // Lower opacity
        Color(0xFF505050).copy(alpha = 0.6f),      // Lower opacity
        Color(0xFF3D3D3D).copy(alpha = 0.5f),      // Lower opacity
        Color(0xFF2A2A2A).copy(alpha = 0.3f),      // Much lower opacity
    )

    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_animation"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 1000f, translateAnim - 1000f),
        end = Offset(translateAnim, translateAnim),
        tileMode = TileMode.Mirror
    )

    this.background(brush)
}