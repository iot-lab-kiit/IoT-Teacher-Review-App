package `in`.iot.lab.review.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.BookmarkBorder   // ✅ correct import
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.CyberpunkCard
import `in`.iot.lab.design.components.CyberBlue
import `in`.iot.lab.design.components.CyberPurple
import `in`.iot.lab.design.components.StarUI
import `in`.iot.lab.design.theme.GlassBorder
import java.text.DecimalFormat

@Composable
fun FacultyReviewDataUI(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
    experience: Double?,
    avgRating: Double,
    totalRating: Int,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    hazeState: HazeState
) {
    var expanded by remember { mutableStateOf(false) }

    CyberpunkCard(
        modifier = modifier,
        cornerRadius = 22.dp,
        glowAlphaMin = 0.30f,
        glowAlphaMax = 0.70f
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        backgroundColor = Color(0xFF060610),
                        tints = listOf(
                            HazeTint(color = CyberBlue.copy(alpha = 0.05f)),
                            HazeTint(color = CyberPurple.copy(alpha = 0.04f))
                        ),
                        blurRadius = 20.dp,
                        noiseFactor = 0.03f
                    )
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        errorImage = painterResource(id = R.drawable.person),
                        modifier = Modifier.size(56.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(14.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        experience?.let {
                            Text(
                                text = "${DecimalFormat("#.##").format(it)} yrs experience",
                                style = MaterialTheme.typography.labelMedium,
                                color = CyberBlue.copy(alpha = 0.9f)
                            )
                        }
                    }

                    // ✅ Bookmark icon button — filled vs outlined based on isBookmarked
                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isBookmarked) CyberPurple.copy(alpha = 0.20f)
                                else Color.Transparent
                            )
                            .border(
                                1.dp,
                                if (isBookmarked) CyberPurple.copy(alpha = 0.5f)
                                else GlassBorder.copy(alpha = 0.3f),
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = if (isBookmarked)
                                Icons.Filled.Bookmark          // ✅ filled when bookmarked
                            else
                                Icons.Outlined.BookmarkBorder, // ✅ outlined when not
                            contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
                            tint = if (isBookmarked) CyberPurple
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                HorizontalDivider(
                    color = CyberBlue.copy(alpha = 0.12f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = ripple(bounded = true),
                            interactionSource = remember { MutableInteractionSource() }
                        ) { expanded = !expanded }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "OVERALL EXCELLENCE",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberPurple.copy(alpha = 0.90f),
                            letterSpacing = 1.4.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        val rotation by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f,
                            animationSpec = tween(300),
                            label = "arrow"
                        )
                        Icon(
                            Icons.Default.KeyboardArrowDown, null,
                            modifier = Modifier.rotate(rotation),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = DecimalFormat("0.0").format(avgRating),
                                style = MaterialTheme.typography.headlineLarge,
                                color = CyberBlue,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier.padding(start = 3.dp, bottom = 6.dp),
                                text = "/5.0",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            StarUI(rating = avgRating, starSize = 16, showText = false)
                            Spacer(Modifier.height(2.dp))
                            Text(
                                "$totalRating reviews",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.25f))
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "PERFORMANCE BREAKDOWN",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberPurple.copy(alpha = 0.8f),
                            letterSpacing = 1.2.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        PerformanceRow("Teaching", avgRating)
                        PerformanceRow("Conduct", avgRating)
                        PerformanceRow("Marking", avgRating)
                    }
                }
            }
        }
    }
}

@Composable
fun PerformanceRow(title: String, rating: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        StarUI(rating = rating, starSize = 16, showText = false)
    }
}