package `in`.iot.lab.review.view.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import `in`.iot.lab.design.animations.PostAnimation
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.LocalHazeState
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.TertiaryButton
import `in`.iot.lab.design.state.HandleUiState
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.components.AppRatingBar
import `in`.iot.lab.review.view.components.FeedbackTextField
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.review.vm.FacultyViewModel

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

    LaunchedEffect(editingReview) {
        editingReview?.let {
            ratingT = it.rating; ratingB = it.rating; ratingM = it.rating; feedback = it.feedback
        }
    }

    val averageRating = (ratingT + ratingB + ratingM) / 3
    var showDialog by remember { mutableStateOf(false) }

    // ✅ AppScreen creates HazeState internally and provides via LocalHazeState
    // No hazeState parameter needed here anymore
    AppScreen {
        // ✅ Read the HazeState provided by AppScreen
        val hazeState = LocalHazeState.current

        submitState.HandleUiState(
            onTryAgain = { setEvent(FacultyEvent.SubmitReview(averageRating, feedback)) },
            idleBlock = {
                PostReviewIdleScreen(
                    ratingT = ratingT, ratingB = ratingB, ratingM = ratingM,
                    feedback = feedback, isEditing = editingReview != null,
                    onTeachingRatingChange  = { ratingT = it },
                    onBehaviourRatingChange = { ratingB = it },
                    onMarksRatingChange     = { ratingM = it },
                    onFeedbackChange        = { feedback = it },
                    onSubmitClick  = { setEvent(FacultyEvent.SubmitReview(averageRating, feedback)) },
                    onDiscardClick = { viewModel.clearEditingReview(); goBack() },
                    hazeState = hazeState
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

        if (showDialog) PostAnimation(onAnimationComplete = goBack)
    }
}

@Composable
fun PostReviewIdleScreen(
    ratingT: Double, ratingB: Double, ratingM: Double,
    feedback: String, isEditing: Boolean,
    onTeachingRatingChange: (Double) -> Unit,
    onBehaviourRatingChange: (Double) -> Unit,
    onMarksRatingChange: (Double) -> Unit,
    onFeedbackChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onDiscardClick: () -> Unit,
    hazeState: HazeState?
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

        // Rating card — uses haze if available, plain glass fallback if not
        val cardModifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .then(
                if (hazeState != null) {
                    Modifier.hazeEffect(
                        state = hazeState,
                        style = HazeStyle(
                            backgroundColor = Color(0xFF080812),
                            tints = listOf(
                                HazeTint(color = primaryColor.copy(alpha = 0.07f)),
                                HazeTint(color = secondaryColor.copy(alpha = 0.05f))
                            ),
                            blurRadius = 24.dp,
                            noiseFactor = 0.04f
                        )
                    )
                } else {
                    Modifier
                }
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.35f),
                        secondaryColor.copy(alpha = 0.20f)
                    )
                ),
                shape = RoundedCornerShape(22.dp)
            )

        Box(modifier = cardModifier) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                RatingRowItem("Teaching",  ratingT.toFloat())  { onTeachingRatingChange(it.toDouble()) }
                HorizontalDivider(color = GlassBorderSubtle, thickness = 1.dp)
                RatingRowItem("Behaviour", ratingB.toFloat())  { onBehaviourRatingChange(it.toDouble()) }
                HorizontalDivider(color = GlassBorderSubtle, thickness = 1.dp)
                RatingRowItem("Marks",     ratingM.toFloat())  { onMarksRatingChange(it.toDouble()) }
                Spacer(Modifier.height(8.dp))
                FeedbackTextField(input = feedback, onInputChanged = onFeedbackChange)
            }
        }

        PrimaryButton(
            onClick = {
                if (feedback.isNotEmpty()) onSubmitClick()
                else Toast.makeText(context, "Please enter your feedback", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text(
                text = if (isEditing) "Update Review" else "Submit Review",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        TertiaryButton(
            onClick = onDiscardClick,
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text(
                text = if (isEditing) "Cancel" else "Discard Review",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(60.dp))
    }
}

@Composable
private fun RatingRowItem(label: String, rating: Float, onRatingChange: (Float) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        AppRatingBar(rating = rating, itemSize = 32.dp, space = 6.dp, onRatingChange = onRatingChange)
    }
}