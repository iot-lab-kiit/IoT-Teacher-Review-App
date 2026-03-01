package `in`.iot.lab.review.view.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.animations.PostAnimation
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TertiaryButton
import `in`.iot.lab.design.state.HandleUiState
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.design.theme.GlassBorder
import `in`.iot.lab.design.theme.GlassSurface
import `in`.iot.lab.design.theme.primaryColor
import `in`.iot.lab.design.theme.surfaceVariantColor
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.components.AppRatingBar
import `in`.iot.lab.review.view.components.FeedbackTextField
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.vm.FacultyViewModel


// -------------------- PREVIEW --------------------

@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        AppScreen {
            PostReviewIdleScreen(
                ratingT = 1.0,
                ratingB = 1.0,
                ratingM = 1.0,
                feedback = "",
                isEditing = false,
                onTeachingRatingChange = {},
                onBehaviourRatingChange = {},
                onMarksRatingChange = {},
                onFeedbackChange = {},
                onSubmitClick = {},
                onDiscardClick = {}
            )
        }
    }
}


// -------------------- SCREEN CONTROL --------------------

@Composable
fun PostReviewScreenControl(
    submitState: UiState<Unit>,
    goBack: () -> Unit,
    setEvent: (FacultyEvent) -> Unit,
    viewModel: FacultyViewModel
) {

    val editingReview by viewModel.editingReview.collectAsState()

    var ratingT by remember { mutableDoubleStateOf(1.0) }
    var ratingB by remember { mutableDoubleStateOf(1.0) }
    var ratingM by remember { mutableDoubleStateOf(1.0) }
    var feedback by remember { mutableStateOf("") }

    // Prefill values when editing
    LaunchedEffect(editingReview) {
        editingReview?.let {
            ratingT = it.rating
            ratingB = it.rating
            ratingM = it.rating
            feedback = it.feedback
        }
    }

    val averageRating = (ratingT + ratingB + ratingM) / 3

    var showDialog by remember { mutableStateOf(false) }

    AppScreen {

        submitState.HandleUiState(
            onTryAgain = {
                setEvent(FacultyEvent.SubmitReview(averageRating, feedback))
            },
            idleBlock = {
                PostReviewIdleScreen(
                    ratingT = ratingT,
                    ratingB = ratingB,
                    ratingM = ratingM,
                    feedback = feedback,
                    isEditing = editingReview != null,
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


// -------------------- IDLE SCREEN --------------------

@Composable
fun PostReviewIdleScreen(
    ratingT: Double,
    ratingB: Double,
    ratingM: Double,
    feedback: String,
    isEditing: Boolean,
    onTeachingRatingChange: (Double) -> Unit,
    onBehaviourRatingChange: (Double) -> Unit,
    onMarksRatingChange: (Double) -> Unit,
    onFeedbackChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onDiscardClick: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(top = 32.dp)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ── Page Title ──
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = if (isEditing) "Edit Your Feedback" else "Submit Your Feedback",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Rate your experience with this faculty",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // ── Rating Glass Card ──
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            GlassSurface.copy(alpha = 0.97f),
                            surfaceVariantColor.copy(alpha = 0.90f)
                        )
                    )
                )
        ) {
            // Top blue glow border
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = primaryColor.copy(alpha = 0.35f)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                // Teaching
                RatingRowItem(
                    label = "Teaching",
                    rating = ratingT.toFloat(),
                    onRatingChange = { onTeachingRatingChange(it.toDouble()) }
                )

                RatingDivider()

                // Behaviour
                RatingRowItem(
                    label = "Behaviour",
                    rating = ratingB.toFloat(),
                    onRatingChange = { onBehaviourRatingChange(it.toDouble()) }
                )

                RatingDivider()

                // Marks
                RatingRowItem(
                    label = "Marks",
                    rating = ratingM.toFloat(),
                    onRatingChange = { onMarksRatingChange(it.toDouble()) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Feedback text field
                FeedbackTextField(
                    input = feedback,
                    onInputChanged = onFeedbackChange
                )
            }
        }

        // ── Submit Button ──
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
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = if (isEditing) "Update Review" else "Submit Review",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        // ── Discard / Cancel Button ──
        TertiaryButton(
            onClick = onDiscardClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = if (isEditing) "Cancel" else "Discard Review",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}


// ── Rating Row: label left, stars right ──
@Composable
private fun RatingRowItem(
    label: String,
    rating: Float,
    onRatingChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        AppRatingBar(
            rating = rating,
            itemSize = 32.dp,
            space = 6.dp,
            onRatingChange = onRatingChange
        )
    }
}


// ── Subtle divider between rating rows ──
@Composable
private fun RatingDivider() {
    HorizontalDivider(
        color = GlassBorder.copy(alpha = 0.35f),
        thickness = 1.dp
    )
}