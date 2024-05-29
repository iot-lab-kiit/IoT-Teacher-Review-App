package `in`.iot.lab.review.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TertiaryButton
import `in`.iot.lab.review.view.components.FeedbackTextField
import `in`.iot.lab.review.view.components.AppRatingBar
import `in`.iot.lab.review.view.events.FacultyEvent


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppScreen {
        PostReviewIdleScreen(
            onSubmitClick = { _, _ -> },
            onDiscardClick = {}
        )
    }
}


@Composable
fun PostReviewScreenControl(
    onDiscardClick: () -> Unit,
    setEvent: (FacultyEvent) -> Unit
) {
    AppScreen {
        PostReviewIdleScreen(
            onSubmitClick = { rating, feedback ->
                setEvent(FacultyEvent.SubmitReview(rating, feedback))
            },
            onDiscardClick = onDiscardClick
        )
    }
}


@Composable
fun PostReviewIdleScreen(
    onSubmitClick: (Double, String) -> Unit,
    onDiscardClick: () -> Unit
) {

    var rating by remember { mutableDoubleStateOf(1.0) }
    var feedback by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Submit Your Feedback",
            style = MaterialTheme.typography.titleLarge
        )


        // App Rating Bar
        AppRatingBar(rating = rating.toFloat()) {
            rating = it.toDouble()
        }


        // Feedback TextField
        FeedbackTextField(input = feedback) {
            feedback = it
        }


        // Submit Button
        PrimaryButton(
            onClick = { onSubmitClick(rating, feedback) },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Submit Review",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        // Discard Button
        TertiaryButton(
            onClick = onDiscardClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Discard Review",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}