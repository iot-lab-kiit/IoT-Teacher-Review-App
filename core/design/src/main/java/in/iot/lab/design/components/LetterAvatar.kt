package `in`.iot.lab.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.design.theme.ProfileColorPalette
import kotlin.math.abs

@Composable
fun LetterAvatar(
    name: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    backgroundColor: Color? = null
) {

    val letter = name
        ?.trim()
        ?.firstOrNull { it.isLetter() }
        ?.uppercase()
        ?.toString()
        ?: "?"

    val resolvedBgColor = backgroundColor ?: profileColorFromName(name)
    val fontScale = when {
        size >= 100.dp -> 0.45f
        size >= 64.dp -> 0.42f
        else -> 0.40f
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(resolvedBgColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            fontSize = (size.value * fontScale).sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )
    }
}

private fun profileColorFromName(name: String?): Color {
    if (name.isNullOrBlank()) {
        return ProfileColorPalette.first()
    }
    val hash = name.trim().lowercase().hashCode()
    val index = abs(hash) % ProfileColorPalette.size
    return ProfileColorPalette[index]
}

//Preview
@Preview(showBackground = true)
@Composable
private fun LetterAvatarPreview() {
    CustomAppTheme {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Large profile
            LetterAvatar(
                name = "Anirban Basak",
                size = 120.dp
            )

            // Small (review style)
            LetterAvatar(
                name = "IoT Lab",
                size = 48.dp
            )

            // Very small edge case
            LetterAvatar(
                name = "X",
                size = 32.dp
            )

            // Null / blank test
            LetterAvatar(
                name = "",
                size = 48.dp
            )
        }
    }
}
