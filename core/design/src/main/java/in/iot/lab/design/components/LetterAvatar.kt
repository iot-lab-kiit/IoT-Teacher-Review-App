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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.theme.CustomAppTheme
import kotlin.math.abs

// Blue-purple-grey gradient pairs (inner → outer)
private val AvatarGradients = listOf(
    Pair(Color(0xFF4D8EFF), Color(0xFF0D2454)),   // vivid blue
    Pair(Color(0xFF9B72FF), Color(0xFF1E1040)),   // soft purple
    Pair(Color(0xFF6B7FCC), Color(0xFF151C3A)),   // blue-grey
    Pair(Color(0xFF3A6FE8), Color(0xFF0A1A3E)),   // royal blue
    Pair(Color(0xFF7C5CE8), Color(0xFF18103A)),   // violet
    Pair(Color(0xFF5B8DEF), Color(0xFF0C1E45)),   // cornflower
    Pair(Color(0xFF8B6FFF), Color(0xFF1A1035)),   // lavender
    Pair(Color(0xFF4A7FD4), Color(0xFF0E1C38)),   // steel blue
)

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

    val fontScale = when {
        size >= 100.dp -> 0.45f
        size >= 64.dp  -> 0.42f
        else           -> 0.40f
    }

    val gradient = if (backgroundColor != null) {
        Brush.radialGradient(listOf(backgroundColor, backgroundColor))
    } else {
        val hash = name?.trim()?.lowercase()?.hashCode() ?: 0
        val (inner, outer) = AvatarGradients[abs(hash) % AvatarGradients.size]
        Brush.radialGradient(colors = listOf(inner, outer))
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(gradient),
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

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun LetterAvatarPreview() {
    CustomAppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LetterAvatar(name = "Anirban Basak", size = 120.dp)
            LetterAvatar(name = "IoT Lab",       size = 48.dp)
            LetterAvatar(name = "Harsh Singh",   size = 48.dp)
            LetterAvatar(name = "",              size = 48.dp)
        }
    }
}