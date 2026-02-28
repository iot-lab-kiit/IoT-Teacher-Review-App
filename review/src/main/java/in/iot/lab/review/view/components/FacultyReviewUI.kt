package `in`.iot.lab.review.view.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import `in`.iot.lab.design.theme.CustomAppTheme
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
                    onBookmarkClick = {/**/}
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

    // Color of the Card
    val cardColor = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    )

    Box(modifier = modifier)
    {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(20.dp),
            colors = cardColor,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Avatar
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        errorImage = painterResource(id = R.drawable.person),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(56.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Name + Experience (takes remaining space)
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {

                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        experience?.let {
                            Text(
                                text = "Experience · ${DecimalFormat("#.##").format(it)} years",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Bookmark (now INSIDE the row properly)
                    IconButton(
                        onClick = onBookmarkClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isBookmarked)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                else
                                    Color.Transparent
                            )
                    ) {
                        Icon(
                            imageVector = if (isBookmarked)
                                Icons.Default.Bookmark
                            else
                                Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = if (isBookmarked)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            indication = rememberRipple(bounded = true),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            expanded = !expanded
                        }
                        .padding(16.dp)
                ) {

                    // Title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "OVERALL EXCELLENCE",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 1.sp
                        )

                        val rotation by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f,
                            animationSpec = tween(300)
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.rotate(rotation),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Rating Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Left: Rating value
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {

                            Text(
                                text = DecimalFormat("0.0").format(avgRating),
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
                                text = "/5.0",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Right: Stars + review count
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {

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
                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {

                            // Section Title
                            Text(
                                text = "PERFORMANCE METRICS",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Teaching
                            PerformanceRow(
                                title = "Teaching",
                                rating = avgRating
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // Behaviour
                            PerformanceRow(
                                title = "Conduct",
                                rating = avgRating
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // Marks
                            PerformanceRow(
                                title = "Marking",
                                rating = avgRating
                            )
                        }
                    }
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
            style = MaterialTheme.typography.bodyMedium
        )

        StarUI(
            rating = rating,
            starSize = 18,
            showText = false
        )
    }
}
