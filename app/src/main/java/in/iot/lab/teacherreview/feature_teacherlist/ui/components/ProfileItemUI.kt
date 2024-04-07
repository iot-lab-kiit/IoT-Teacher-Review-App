package `in`.iot.lab.teacherreview.feature_teacherlist.ui.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.teacherreview.R
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
            headingTitle = R.string.roll_number,
            leadingIcon = Icons.Default.Person,
            fieldValue = "21051880"
        )
    }
}

/**
 * This function draws each Profile Screen UI Cards
 *
 * @param modifier Default to pass modifications from the Parent Class
 * @param headingTitle This is the heading of the type of Data
 * @param leadingIcon This is the leading Icon of the Card
 * @param fieldValue This is the data of the Card
 */
@Composable
fun ProfileItemUI(
    modifier: Modifier = Modifier,
    headingTitle: Int,
    leadingIcon: ImageVector,
    fieldValue: String
) {

    // Heading Title to be written
    Text(
        text = stringResource(id = headingTitle),
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    )

    // Main Card which contains the data
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {

        // Contains the Leading Icon and Data
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            // Leading Icon
            Icon(
                imageVector = leadingIcon,
                contentDescription = stringResource(id = R.string.profile)
            )

            // Spacing of 8 dp
            Spacer(modifier = Modifier.width(8.dp))

            // Data to be shown
            Text(
                text = fieldValue,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}