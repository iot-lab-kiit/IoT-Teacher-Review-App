package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.User

// This is the Preview function of the Teacher Review Control Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewControl() {
    CustomAppTheme {
        ReviewCardItem(
            createdBy = User(
                name = "Anirban Basak",
            ),
            review = "If you know how to deal with few " +
                    "seniors who have ego issues, you will " +
                    "ace at your work. Just learn how to take" +
                    " command over few egoistic coordinators " +
                    "or HODs, DPS will be the best place for " +
                    "work and to build your career."
        )
    }
}

/**
 * This is the UI for Each Card Item of the Review Section
 *
 * @param modifier Default so that the parent can pass modifications to the Child
 * @param createdBy This is the User who created the Review
 * @param review This is the Review
 */
@Composable
fun ReviewCardItem(
    modifier: Modifier = Modifier,
    createdBy: User,
    review: String
) {

    // This is the Card View
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = modifier
                .padding(8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Profile Photo of the Reviewer
            AsyncImage(
                model = createdBy.pictureUrl,
                placeholder = painterResource(id = R.drawable.profile_photo),
                contentDescription = stringResource(id = R.string.profile),
                modifier = Modifier
                    .size(54.dp)
                    .padding(top = 8.dp, end = 8.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            // This contains the Name of the Reviewer, Rating Stars and his Review
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {

                // Reviewer Name
                Text(
                    text = createdBy.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )

                // Stars given by the Reviewer
                StarsRow(starCount = 4.3)

                // Space of height 4 dp
                Spacer(modifier = Modifier.height(4.dp))

                // This is the review Given by the User
                Text(
                    text = review,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Space of Height 4 dp
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}