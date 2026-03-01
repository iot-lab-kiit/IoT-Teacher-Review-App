package `in`.iot.lab.review.view.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.StarUI
import `in`.iot.lab.design.theme.*
import java.text.DecimalFormat


@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    CustomAppTheme {
        AppScreen {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                FacultyReviewDataUI(
                    name = "Anirban Basak",
                    photoUrl = "",
                    experience = 3.0,
                    avgRating = 4.3,
                    totalRating = 21,
                    isBookmarked = true,
                    onBookmarkClick = {}
                )
            }
        }
    }
}


@Composable
fun FacultyReviewDataUI(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
    experience: Double?,
    avgRating: Double,
    totalRating: Int,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0D1829).copy(alpha = 0.98f),
                        surfaceVariantColor.copy(alpha = 0.92f)
                    )
                )
            )
    ) {
        // Top border glow
        HorizontalDivider(
            modifier = Modifier.align(Alignment.TopCenter),
            thickness = 1.dp,
            color = primaryColor.copy(alpha = 0.3f)
        )

        Column {

            // ── Top Row: Avatar + Name + Bookmark ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Avatar
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.25f),
                                    primaryContainerColor.copy(alpha = 0.15f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        errorImage = painterResource(id = R.drawable.person),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(56.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    experience?.let {
                        Text(
                            text = "${DecimalFormat("#.##").format(it)} yrs experience",
                            style = MaterialTheme.typography.labelMedium,
                            color = primaryColor.copy(alpha = 0.8f)
                        )
                    }
                }

                // Bookmark button
                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isBookmarked)
                                primaryColor.copy(alpha = 0.18f)
                            else
                                GlassBorder.copy(alpha = 0.2f)
                        )
                ) {
                    Icon(
                        imageVector = if (isBookmarked)
                            Icons.Default.Bookmark
                        else
                            Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isBookmarked)
                            primaryColor
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // ── Divider ──
            HorizontalDivider(
                color = GlassBorder.copy(alpha = 0.4f),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // ── Rating Section (expandable) ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = rememberRipple(bounded = true),
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
                        color = primaryColor.copy(alpha = 0.7f),
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    val rotation by animateFloatAsState(
                        targetValue = if (expanded) 180f else 0f,
                        animationSpec = tween(300),
                        label = "arrow"
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.rotate(rotation),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Big rating number
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = DecimalFormat("0.0").format(avgRating),
                            style = MaterialTheme.typography.headlineLarge,
                            color = primaryColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(start = 3.dp, bottom = 6.dp),
                            text = "/5.0",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Stars + count
                    Column(horizontalAlignment = Alignment.End) {
                        StarUI(
                            rating = avgRating,
                            starSize = 16,
                            showText = false
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "$totalRating reviews",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // ── Expanded Metrics ──
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor.copy(alpha = 0.4f))
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "PERFORMANCE BREAKDOWN",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor.copy(alpha = 0.7f),
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    PerformanceRow(title = "Teaching",  rating = avgRating)
                    PerformanceRow(title = "Conduct",   rating = avgRating)
                    PerformanceRow(title = "Marking",   rating = avgRating)
                }
            }
        }
    }
}


@Composable
fun PerformanceRow(
    title: String,
    rating: Double
) {
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
        StarUI(
            rating = rating,
            starSize = 16,
            showText = false
        )
    }
}