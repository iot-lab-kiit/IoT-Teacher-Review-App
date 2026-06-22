package `in`.iot.lab.design.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Cyberpunk color tokens
val CyberBlue   = Color(0xFF4D8EFF)
val CyberPurple = Color(0xFF9B72FF)
val CyberBlueGlow   = Color(0xFF1A4FCC)
val CyberPurpleGlow = Color(0xFF6B3DB8)
val DeepBlack   = Color(0xFF000000)
val NearBlack   = Color(0xFF04040A)

/**
 * Animated ambient background with 4 slowly drifting light orbs.
 * Place this at the very bottom of any screen's Box stack.
 *
 * Usage:
 *   Box(Modifier.fillMaxSize()) {
 *       CyberpunkBackground()
 *       // ... your content
 *   }
 */
@Composable
fun CyberpunkBackground(modifier: Modifier = Modifier) {

    // Each orb gets its own infinitely repeating offset animation
    // with different durations so they never sync up — creates organic drift

    val infiniteTransition = rememberInfiniteTransition(label = "cyberBg")

    // Orb 1 — top-left blue glow, slow drift
    val orb1X by infiniteTransition.animateFloat(
        initialValue = -200f, targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o1x"
    )
    val orb1Y by infiniteTransition.animateFloat(
        initialValue = -300f, targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o1y"
    )

    // Orb 2 — bottom-right purple glow
    val orb2X by infiniteTransition.animateFloat(
        initialValue = 900f, targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o2x"
    )
    val orb2Y by infiniteTransition.animateFloat(
        initialValue = 2000f, targetValue = 1400f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o2y"
    )

    // Orb 3 — mid blue, subtle
    val orb3X by infiniteTransition.animateFloat(
        initialValue = 700f, targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o3x"
    )
    val orb3Y by infiniteTransition.animateFloat(
        initialValue = 600f, targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(27000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o3y"
    )

    // Orb 4 — top-right faint purple
    val orb4X by infiniteTransition.animateFloat(
        initialValue = 800f, targetValue = 400f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o4x"
    )
    val orb4Y by infiniteTransition.animateFloat(
        initialValue = -100f, targetValue = 400f,
        animationSpec = infiniteRepeatable(
            animation = tween(19000, easing = SinusoidalEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "o4y"
    )

    Box(modifier = modifier.fillMaxSize()) {

        // Base — pure deep black
        Box(
            modifier = Modifier.fillMaxSize()
                .background(DeepBlack)
        )

        // Orb 1 — strong blue, top left corner
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(CyberBlue.copy(alpha = 0.13f), Color.Transparent),
                        center = Offset(orb1X, orb1Y),
                        radius = 700f
                    )
                )
        )

        // Orb 2 — strong purple, bottom right
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(CyberPurple.copy(alpha = 0.11f), Color.Transparent),
                        center = Offset(orb2X, orb2Y),
                        radius = 750f
                    )
                )
        )

        // Orb 3 — mid blue, center-ish
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(CyberBlueGlow.copy(alpha = 0.08f), Color.Transparent),
                        center = Offset(orb3X, orb3Y),
                        radius = 500f
                    )
                )
        )

        // Orb 4 — faint purple, top right
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(CyberPurpleGlow.copy(alpha = 0.09f), Color.Transparent),
                        center = Offset(orb4X, orb4Y),
                        radius = 600f
                    )
                )
        )

        // Subtle vignette — darkens edges slightly for depth
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f)),
                        radius = 1400f
                    )
                )
        )
    }
}

// Sinusoidal easing for natural light drift
private val SinusoidalEasing = CubicBezierEasing(0.37f, 0f, 0.63f, 1f)