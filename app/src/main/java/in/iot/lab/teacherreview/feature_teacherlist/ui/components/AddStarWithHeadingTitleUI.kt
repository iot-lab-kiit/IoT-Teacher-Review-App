package `in`.iot.lab.teacherreview.feature_teacherlist.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        AddStarWithHeadingTitleUI(
            headingTitle = R.string.overall_rating,
            starCount = 5.0,
            onAddClick = { },
            onSubtractClick = { }
        )
    }
}

/**
 * This function makes the Add Rating Star and Title Fields
 *
 * @param modifier Default to pass down modifications from the Parent
 * @param headingTitle This is the Title of the Rating Parameter
 * @param starCount The Number of Stars that should be shown in the UI
 * @param onAddClick This function increases the Star Count
 * @param onSubtractClick This function decreases the Star Count
 */
@Composable
fun AddStarWithHeadingTitleUI(
    modifier: Modifier = Modifier,
    headingTitle: Int,
    starCount: Double,
    onAddClick: () -> Unit,
    onSubtractClick: () -> Unit
) {

    // Parent Composable which contains the Components
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        // Heading Title to be written
        Text(
            text = stringResource(id = headingTitle),
            style = MaterialTheme.typography.titleMedium,
        )

        // This row Contains the Stars , Add and Minus Button
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // This function draws the Stars
            StarsRow(
                modifier = Modifier
                    .size(24.dp),
                starCount = starCount
            )

            // This Row Contains the Add and Minus Button
            Row {

                // Add Button
                Button(
                    onClick = onAddClick,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    contentPadding = PaddingValues(0.dp),
                ) {

                    // Add Icon
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }

                // Spacing of 8 dp
                Spacer(modifier = Modifier.width(8.dp))

                // Minus Button
                Button(
                    onClick = onSubtractClick,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {

                    // Minus Icon
                    Icon(
                        painter = painterResource(id = R.drawable.image_minus),
                        contentDescription = stringResource(id = R.string.minus),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            }
        }
    }
}