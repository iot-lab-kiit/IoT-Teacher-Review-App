package `in`.iot.lab.history.view.components

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppNetworkImage
import `in`.iot.lab.design.components.StarUI
import `in`.iot.lab.design.theme.CustomAppTheme


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
        ReviewDataUI(
            title = "Anirban Basak",
            rating = 4.8,
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting " +
                    "industry. Lorem Ipsum has been the industry's standard dummy text ever " +
                    "since the 1500s, when an unknown printer took a galley of type and " +
                    "scrambled it to make a type specimen book.",
            photoUrl = ""
        )
    }
}


@Composable
fun ReviewDataUI(
    modifier: Modifier = Modifier,
    title: String,
    rating: Double,
    description: String,
    photoUrl: String
) {

    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors()
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Profile Pic Image
                AppNetworkImage(
                    model = photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(48.dp),
                    contentScale = ContentScale.Fit
                )

                Column {

                    // Name Text
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        // This function shows the Star UI
                        StarUI(rating = rating)

                        // Creation Date Text
                        Text(
                            text = "TODO : 28-09-2024",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            // Description Text
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}