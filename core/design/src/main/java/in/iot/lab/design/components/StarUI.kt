package `in`.iot.lab.design.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        Column {
            StarUI(rating = 5.0)
            StarUI(rating = 4.7)
            StarUI(rating = 4.5)
            StarUI(rating = 4.2)
            StarUI(rating = 3.2, showText = true)
        }
    }
}


/**
 * This function is used to display the star rating along with an optional [showText] boolean
 * to opt showing the rating value text.
 *
 * @param modifier The modifier to be applied to the composable.
 * @param rating The rating stars to be displayed.
 * @param showText Whether to show the rating value text.
 */
@Composable
fun StarUI(
    modifier: Modifier = Modifier,
    rating: Double,
    showText: Boolean = false
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Rendering the Stars
        for (i in 0 until 5) {
            when {

                // Full Stars
                (rating - i) >= 1 -> {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "star",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Half Stars
                (rating - i) >= 0.5 -> {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.AutoMirrored.Filled.StarHalf,
                        contentDescription = "star",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Empty Stars / Outlined Stars
                else -> {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Outlined.StarOutline,
                        contentDescription = "star",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Stars value text
        if (showText)
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = DecimalFormat("#.##").format(rating),
                style = MaterialTheme.typography.labelMedium
            )
    }
}