package `in`.iot.lab.review.view.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.R
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.LetterAvatar
import `in`.iot.lab.design.components.StarUI
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.design.theme.RatingHigh
import `in`.iot.lab.design.theme.RatingLow
import `in`.iot.lab.design.theme.RatingMedium
import java.text.DecimalFormat


// Preview Function
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
                FacultyDataUI(
                    name = "Anirban Basak",
                    photoUrl = "",
                    experience = 3.0,
                    avgRating = 4.3,
                    totalRating = 21
                )

                FacultyDataUI(
                    name = "Anirban Basak",
                    photoUrl = "",
                    experience = 2.4444444,
                    avgRating = 2.1,
                    totalRating = 21
                )

                FacultyDataUI(
                    name = "Anirban Basak",
                    photoUrl = "",
                    experience = null,
                    avgRating = 1.3,
                    totalRating = 2
                )

                FacultyDataUI(
                    name = "Anirban Basak",
                    photoUrl = "",
                    experience = 3.0,
                    avgRating = 0.0,
                    totalRating = 21
                )
            }
        }
    }
}


/**
 * This function creates a Faculty List Item UI containing the data of a single faculty.
 *
 * @param modifier Modifier for the composable
 * @param name Name of the faculty
 * @param photoUrl Photo URL of the faculty
 * @param experience Experience of the faculty
 * @param avgRating Average rating of the faculty
 * @param totalRating Total number of ratings
 */
@Composable
fun FacultyDataUI(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
    experience: Double?,
    avgRating: Double,
    totalRating: Int
) {

    Card(
        modifier = modifier
            .padding(horizontal = 6.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {

        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Avatar
                if (photoUrl.isNotBlank()) {
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        errorImage = painterResource(id = R.drawable.person),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(56.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    LetterAvatar(
                        name = name,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    experience?.let {
                        Text(
                            text = "Experience · ${DecimalFormat("#.##").format(it)} years",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RatingBadge(avgRating)

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "· $totalRating Ratings",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingBadge(rating: Double) {

    val badgeColor = when {
        rating >= 4 -> RatingHigh
        rating >= 2 -> RatingMedium
        rating > 0 -> RatingLow
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = Color.White

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(badgeColor.copy(alpha = 0.9f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "★ ${DecimalFormat("#.##").format(rating)}",
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}