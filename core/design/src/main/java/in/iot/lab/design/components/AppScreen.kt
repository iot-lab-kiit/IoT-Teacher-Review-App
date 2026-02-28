package `in`.iot.lab.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.CustomAppTheme
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import `in`.iot.lab.design.theme.GradientTop
import `in`.iot.lab.design.theme.GradientMiddle
import `in`.iot.lab.design.theme.GradientBottom


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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to GradientTop,
                                0.30f to GradientMiddle,
                                1.0f to GradientBottom
                            )
                        )
                    )
            ) {

                // subtle top glow layer
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.035f),
                                    Color.Transparent
                                ),
                                radius = 750f
                            )
                        )
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = contentAlignment,
                    content = content
                )
            }
        }
    }
}