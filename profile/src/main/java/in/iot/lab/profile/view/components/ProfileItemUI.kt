package `in`.iot.lab.profile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.*

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun Preview() {
    CustomAppTheme {
        ProfileItemUI(
            title = "Email Id",
            leadingIcon = Icons.Default.Person,
            description = "test@kiit.ac.in"
        )
    }
}

@Composable
fun ProfileItemUI(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    leadingIcon: ImageVector
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Label
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = secondaryColor.copy(alpha = 0.85f)   // purple accent label
        )

        // Frosted glass card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            GlassSurface.copy(alpha = 0.95f),
                            surfaceVariantColor.copy(alpha = 0.80f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            GlassBorder.copy(alpha = 0.7f),
                            secondaryColor.copy(alpha = 0.15f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            // Frosted white shimmer overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(GlassOverlay)
            )

            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icon pill — blue tint
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(primaryColor.copy(alpha = 0.12f))
                        .border(
                            width = 1.dp,
                            color = primaryColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}