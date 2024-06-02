package `in`.iot.lab.review.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.StarUI
import `in`.iot.lab.design.theme.CustomAppTheme
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
                    experience = 3.0,
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
    experience: Double,
    avgRating: Double,
    totalRating: Int
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

    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = cardColor,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {

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
                Text(
                    text = "Experience · ${DecimalFormat("#.##").format(experience)} years",
                    style = MaterialTheme.typography.labelLarge
                )


                Row(verticalAlignment = Alignment.CenterVertically) {

                    // This function shows the Star UI
                    StarUI(
                        modifier = Modifier.weight(1f),
                        rating = avgRating,
                        showText = true
                    )

                    // Description Text
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "· $totalRating Ratings",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}