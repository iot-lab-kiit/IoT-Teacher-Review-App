package `in`.iot.lab.review.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.LetterAvatar
import `in`.iot.lab.design.theme.GlassBorder
import `in`.iot.lab.design.theme.primaryColor
import `in`.iot.lab.design.theme.secondaryColor
import java.text.DecimalFormat

private data class RatingTier(val accent: Color, val glow: Color)

private fun tierFor(r: Double) = when {
    r >= 3.5 -> RatingTier(Color(0xFF00E5A0), Color(0xFF00C985))
    r >= 2.5 -> RatingTier(Color(0xFFFFB740), Color(0xFFE09000))
    else     -> RatingTier(Color(0xFFFF6B8A), Color(0xFFD44070))
}

// Evenly blended top-right smudge —
// One single very large radial, no hard core, pure smooth falloff.
// A diagonal linear gradient layered on top distributes it evenly
// across the top-right quadrant rather than being a bright dot.
private fun Modifier.topRightSmudge(tier: RatingTier): Modifier = drawWithContent {
    drawContent()
    val w = size.width
    val h = size.height

    // Layer 1 — large single radial, very gentle peak, wide feather
    // Using a 4-stop gradient so the falloff is gradual with no harsh edge
    drawCircle(
        brush = Brush.radialGradient(
            colorStops = arrayOf(
                0.00f to tier.glow.copy(alpha = 0.22f),
                0.35f to tier.glow.copy(alpha = 0.14f),
                0.65f to tier.glow.copy(alpha = 0.05f),
                1.00f to Color.Transparent
            ),
            center = Offset(w, 0f),
            radius = w * 0.85f           // large radius = wide even spread
        ),
        radius = w * 0.85f,
        center = Offset(w, 0f)
    )

    // Layer 2 — diagonal linear gradient that sweeps color from top-right
    // toward bottom-left, blending it evenly across the card surface
    drawRect(
        brush = Brush.linearGradient(
            colorStops = arrayOf(
                0.00f to tier.glow.copy(alpha = 0.12f),
                0.40f to tier.glow.copy(alpha = 0.04f),
                0.70f to Color.Transparent
            ),
            start = Offset(w, 0f),
            end   = Offset(w * 0.20f, h)
        )
    )
}

@Composable
fun FacultyDataUI(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
    experience: Double?,
    avgRating: Double,
    totalRating: Int,
    hazeState: HazeState,
    description: String? = null,
    onRemoveBookmark: (() -> Unit)? = null
) {
    val tier       = tierFor(avgRating)
    val ratingText = DecimalFormat("0.0").format(avgRating)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .hazeEffect(
                state = hazeState,
                style = HazeStyle(
                    backgroundColor = Color(0xFF07070F),
                    tints = listOf(
                        HazeTint(color = primaryColor.copy(alpha = 0.05f)),
                        HazeTint(color = secondaryColor.copy(alpha = 0.03f))
                    ),
                    blurRadius = 22.dp,
                    noiseFactor = 0.03f
                )
            )
            .topRightSmudge(tier)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.50f),
                        secondaryColor.copy(alpha = 0.28f),
                        GlassBorder.copy(alpha = 0.15f)
                    )
                ),
                shape = RoundedCornerShape(18.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Avatar
            if (photoUrl.isNotBlank()) {
                AppNetworkImage(
                    model = photoUrl,
                    contentDescription = null,
                    errorImage = painterResource(R.drawable.person),
                    modifier = Modifier.size(56.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                LetterAvatar(name = name, size = 56.dp)
            }

            // Text block
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.1.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(tier.accent)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.Default.Star, null,
                                tint = Color.Black.copy(alpha = 0.75f),
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = ratingText,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black.copy(alpha = 0.80f),
                                letterSpacing = 0.2.sp
                            )
                        }
                    }

                    Text(
                        text = "· $totalRating Ratings",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.40f)
                    )
                }

                experience?.let {
                    Text(
                        text = "Exp: ${DecimalFormat("#.##").format(it)} years",
                        style = MaterialTheme.typography.labelSmall,
                        color = tier.accent.copy(alpha = 0.55f)
                    )
                }

                if (!description.isNullOrBlank()) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            Icons.Outlined.School, null,
                            tint = tier.accent.copy(alpha = 0.45f),
                            modifier = Modifier.size(12.dp).padding(top = 1.dp)
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.38f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            onRemoveBookmark?.let { onRemove ->
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(secondaryColor.copy(alpha = 0.10f))
                        .border(1.dp, secondaryColor.copy(alpha = 0.20f), RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        Icons.Default.BookmarkRemove, null,
                        tint = secondaryColor,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}