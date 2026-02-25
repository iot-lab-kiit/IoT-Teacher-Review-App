package `in`.iot.lab.review.view.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    // Color of the Card
    val cardColor = CardDefaults.cardColors(
        containerColor = when {
            avgRating >= 4 -> Color(0xFF2C4431)
            avgRating >= 2 -> Color(0xFF26444D)
            avgRating > 0 -> Color(0xFF752E2E)
            else -> Color.Unspecified
        }
    )
    Box(modifier = modifier)
    {
        ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = cardColor,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // Profile Pic Image
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        errorImage = painterResource(id = R.drawable.person),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(56.dp),
                        contentScale = ContentScale.Fit
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {

                        // Name Text
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        // Experience of the Faculty
                        experience?.let {
                            Text(
                                text = "Experience · ${DecimalFormat("#.##").format(it)} years",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    // Title
                    Text(
                        text = "OVERALL EXCELLENCE",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

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
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp, bottom = 6.dp),
                                text = "/5.0",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
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
                                text = ". $totalRating reviews",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    // Section Title
                    Text(
                        text = "PERFORMANCE METRICS",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
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
        IconButton(
            onClick = onBookmarkClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top=6.dp, end = 6.dp)
                .size(30.dp)
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
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
            style = MaterialTheme.typography.bodyLarge
        )

        StarUI(
            rating = rating,
            starSize = 18,
            showText = false
        )
    }
}
