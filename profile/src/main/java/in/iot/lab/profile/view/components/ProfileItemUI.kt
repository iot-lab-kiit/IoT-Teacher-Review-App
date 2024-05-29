package `in`.iot.lab.profile.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.*

// This is the Preview function of the Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        ProfileItemUI(
            title = "Email Id",
            leadingIcon = Icons.Default.Person,
            description = "test@kiit.ac.in"
        )
    }
}


/**
 * This function draws each Profile Screen UI Cards
 *
 * @param modifier Default to pass modifications from the Parent Class
 * @param title This is the heading of the type of Data
 * @param leadingIcon This is the leading Icon of the Card
 * @param description This is the data of the Card
 */
@Composable
fun ProfileItemUI(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    leadingIcon: ImageVector
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // Heading Title to be written
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )

        // Main Card which contains the data
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
        ) {

            // Contains the Leading Icon and Data
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Leading Icon
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null
                )

                // Data to be shown
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}