package `in`.iot.lab.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import `in`.iot.lab.design.theme.CustomAppTheme

// ── CompositionLocal so any child can access the screen's HazeState ──
// without prop-drilling through every composable layer
val LocalHazeState = compositionLocalOf<HazeState?> { null }

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = Color.Transparent,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    // Single HazeState created HERE — shared with all descendants via CompositionLocal
    val hazeState = remember { HazeState() }

    CustomAppTheme {
        Surface(
            modifier = modifier,
            shape = shape,
            color = color,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
            border = border
        ) {
            CompositionLocalProvider(LocalHazeState provides hazeState) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        // hazeSource on the background — this is what cards blur against
                        .hazeSource(hazeState)
                ) {
                    // Animated cyberpunk background with drifting light orbs
                    CyberpunkBackground()

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = contentAlignment,
                        content = content
                    )
                }
            }
        }
    }
}