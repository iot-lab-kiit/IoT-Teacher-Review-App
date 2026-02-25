package `in`.iot.lab.review.view.screens

import android.R
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.animations.PostAnimation
import `in`.iot.lab.design.components.TertiaryButton
import `in`.iot.lab.design.state.HandleUiState
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.components.AppRatingBar
import `in`.iot.lab.review.view.components.FeedbackTextField
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.vm.FacultyViewModel


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
                ratingT = 1.0,
                ratingB = 1.0,
                ratingM = 1.0,
                feedback = "",
                onTeachingRatingChange = { },
                onBehaviourRatingChange = { },
                onMarksRatingChange = { },
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
    setEvent: (FacultyEvent) -> Unit,
    viewModel: FacultyViewModel
) {

    var ratingT by remember { mutableDoubleStateOf(1.0) }
    var ratingB by remember { mutableDoubleStateOf(1.0) }
    var ratingM by remember { mutableDoubleStateOf(1.0) }
    var feedback by remember { mutableStateOf("") }

    val averageRating = (ratingT+ratingB+ratingM)/3

    var showDialog by remember { mutableStateOf(false) }
    AppScreen {

        submitState.HandleUiState(
            onTryAgain = { setEvent(FacultyEvent.SubmitReview(averageRating, feedback)) },
            idleBlock = {
                PostReviewIdleScreen(
                    ratingT = ratingT,
                    ratingB = ratingB,
                    ratingM = ratingM,
                    feedback = feedback,
                    onTeachingRatingChange = { ratingT = it },
                    onBehaviourRatingChange = { ratingB = it },
                    onMarksRatingChange = { ratingM = it },
                    onFeedbackChange = { feedback = it },
                    onSubmitClick = {
                        setEvent(FacultyEvent.SubmitReview(averageRating, feedback))
                    },
                    onDiscardClick = {
                        viewModel.clearEditingReview()
                        goBack()
                    }
                )
            },
            onCancel = {
                viewModel.clearEditingReview()
                setEvent(FacultyEvent.ResetSubmitState)
                goBack()
            }
        ) {
            showDialog = true
            setEvent(FacultyEvent.ResetSubmitState)
        }

        if (showDialog) {
            PostAnimation(onAnimationComplete = goBack)
        }
    }
}


@Composable
fun PostReviewIdleScreen(
    ratingT: Double,
    ratingB: Double,
    ratingM: Double,
    feedback: String,
    onTeachingRatingChange: (Double) -> Unit,
    onBehaviourRatingChange: (Double) -> Unit,
    onMarksRatingChange: (Double) -> Unit,
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
            text = if (isEditing)
                "Edit Your Feedback"
            else
                "Submit Your Feedback",
            style = MaterialTheme.typography.titleLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth() ,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Teaching",
                    style = MaterialTheme.typography.titleMedium
                )
                // App Rating Bar
                AppRatingBar(rating = ratingT.toFloat()) {
                    onTeachingRatingChange(it.toDouble())
                }
                Text(
                    text = "Behaviour",
                    style = MaterialTheme.typography.titleMedium
                )
                AppRatingBar(rating = ratingB.toFloat()) {
                    onBehaviourRatingChange(it.toDouble())
                }
                Text(
                    text = "Marks",
                    style = MaterialTheme.typography.titleMedium
                )
                AppRatingBar(rating = ratingM.toFloat()) {
                    onMarksRatingChange(it.toDouble())
                }
                // Feedback TextField
                FeedbackTextField(
                    input = feedback,
                ) {
                    onFeedbackChange(it)
                }
            }
        }


        val context = LocalContext.current

        // Submit Button
        PrimaryButton(
            onClick = {
                if (feedback.isNotEmpty())
                    onSubmitClick()
                else
                    Toast.makeText(
                        context,
                        "Please enter your feedback",
                        Toast.LENGTH_SHORT
                    ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = if (isEditing)
                    "Update Review"
                else
                    "Submit Review",
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
                text = if (isEditing) "Cancel" else "Discard Review",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}