package `in`.iot.lab.design.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A card container with:
 * - Animated glowing border (blue→purple gradient that pulses in alpha)
 * - Moving scan line that sweeps top to bottom like a holographic effect
 * - Corner light flares
 *
 * Usage:
 *   CyberpunkCard { /* your card content */ }
 */
@Composable
fun CyberpunkCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 18.dp,
    glowAlphaMin: Float = 0.25f,
    glowAlphaMax: Float = 0.65f,
    showScanLine: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cyberCard")

    // Pulsing border alpha
    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = glowAlphaMin,
        targetValue = glowAlphaMax,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "borderAlpha"
    )

    // Scan line Y position — sweeps top to bottom every 4s
    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "scanLine"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0A0A18),
                        Color(0xFF080810)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        CyberBlue.copy(alpha = borderAlpha),
                        CyberPurple.copy(alpha = borderAlpha * 0.7f),
                        CyberBlue.copy(alpha = borderAlpha * 0.4f)
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .then(
                if (showScanLine) {
                    Modifier.drawWithContent {
                        drawContent()
                        // Scan line — thin horizontal beam sweeping downward
                        val y = scanLineY * size.height
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    CyberBlue.copy(alpha = 0.07f),
                                    CyberBlue.copy(alpha = 0.12f),
                                    CyberBlue.copy(alpha = 0.07f),
                                    Color.Transparent
                                ),
                                startY = y - 40f,
                                endY = y + 40f
                            ),
                            blendMode = BlendMode.Screen
                        )
                        // Corner flares — top-left and bottom-right
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(CyberBlue.copy(alpha = 0.15f), Color.Transparent),
                                center = Offset(0f, 0f),
                                radius = 120f
                            ),
                            blendMode = BlendMode.Screen
                        )
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(CyberPurple.copy(alpha = 0.12f), Color.Transparent),
                                center = Offset(size.width, size.height),
                                radius = 120f
                            ),
                            blendMode = BlendMode.Screen
                        )
                    }
                } else Modifier
            )
    ) {
        content()
    }
}

/**
 * Neon glow modifier — adds a colored outer glow to any composable.
 * Use on buttons, avatars, important elements.
 */
fun Modifier.neonGlow(
    color: Color = CyberBlue,
    glowRadius: Dp = 8.dp,
    alpha: Float = 0.4f
): Modifier = this.drawWithContent {
    drawContent()
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(color.copy(alpha = alpha), Color.Transparent),
            center = Offset(size.width / 2, size.height / 2),
            radius = size.width / 2 + glowRadius.toPx()
        ),
        blendMode = BlendMode.Screen
    )
}