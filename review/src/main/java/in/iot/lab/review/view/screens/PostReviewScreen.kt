package `in`.iot.lab.review.view.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TertiaryButton
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.network.state.UiState
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
    CustomAppTheme {
        AppScreen {
            PostReviewIdleScreen(
                rating = 1.0,
                feedback = "",
                onRatingChange = { },
                onFeedbackChange = { },
                onSubmitClick = { },
                onDiscardClick = {}
            )
        }
    }
}


@Composable
fun PostReviewScreenControl(
    submitState: UiState<Unit>,
    goBack: () -> Unit,
    setEvent: (FacultyEvent) -> Unit
) {

    var rating by remember { mutableDoubleStateOf(1.0) }
    var feedback by remember { mutableStateOf("") }

    AppScreen {

        when (submitState) {

            is UiState.Idle -> {
                PostReviewIdleScreen(
                    rating = rating,
                    feedback = feedback,
                    onRatingChange = { rating = it },
                    onFeedbackChange = { feedback = it },
                    onSubmitClick = {
                        setEvent(FacultyEvent.SubmitReview(rating, feedback))
                    },
                    onDiscardClick = goBack
                )
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                Toast.makeText(
                    LocalContext.current,
                    "Review Submitted",
                    Toast.LENGTH_SHORT
                ).show()
                goBack()
                setEvent(FacultyEvent.ResetSubmitState)
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = submitState.message,
                    onCancel = {},
                    onTryAgain = {
                        setEvent(FacultyEvent.SubmitReview(rating, feedback))
                    }
                )
            }
        }
    }
}


@Composable
fun PostReviewIdleScreen(
    rating: Double,
    feedback: String,
    onRatingChange: (Double) -> Unit,
    onFeedbackChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onDiscardClick: () -> Unit
) {

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
            onRatingChange(it.toDouble())
        }


        // Feedback TextField
        FeedbackTextField(input = feedback) {
            onFeedbackChange(it)
        }


        // Submit Button
        PrimaryButton(
            onClick = { onSubmitClick() },
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