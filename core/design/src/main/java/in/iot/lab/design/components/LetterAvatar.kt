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

// Navy-blue tinted avatar gradient pairs: (outer, inner)
private val AvatarGradients = listOf(
    Pair(Color(0xFF1A3A6B), Color(0xFF2A5298)),
    Pair(Color(0xFF0D2E5A), Color(0xFF1A6BFF)),
    Pair(Color(0xFF1B2A4A), Color(0xFF3B5BDB)),
    Pair(Color(0xFF0F2340), Color(0xFF1971C2)),
    Pair(Color(0xFF162032), Color(0xFF228BE6)),
    Pair(Color(0xFF1A2C4E), Color(0xFF4263EB)),
    Pair(Color(0xFF0C1F3F), Color(0xFF1864AB)),
    Pair(Color(0xFF172135), Color(0xFF2B4ACB)),
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
        val (outer, inner) = AvatarGradients[abs(hash) % AvatarGradients.size]
        Brush.radialGradient(
            colors = listOf(inner, outer),
        )
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

@Preview(showBackground = true)
@Composable
private fun LetterAvatarPreview() {
    CustomAppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LetterAvatar(name = "Anirban Basak", size = 120.dp)
            LetterAvatar(name = "IoT Lab",       size = 48.dp)
            LetterAvatar(name = "X",             size = 32.dp)
            LetterAvatar(name = "",              size = 48.dp)
        }
    }
}