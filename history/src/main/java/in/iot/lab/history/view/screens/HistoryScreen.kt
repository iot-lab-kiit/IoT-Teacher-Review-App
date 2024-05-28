package `in`.iot.lab.history.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.history.view.components.ReviewDataUI
import `in`.iot.lab.history.view.event.HistoryEvent
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse


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

            val review = RemoteReviewHistoryResponse(
                id = "",
                createdBy = "",
                createdFor = RemoteFaculty(
                    id = "",
                    name = "Teacher Name",
                    photoUrl = "NaN",
                    experience = 2.0,
                    avgRating = 0.9,
                    totalRating = 24,
                    createdAt = "",
                    updatedAt = "",
                    v = 0
                ),
                rating = 4.0,
                feedback = "",
                createdAt = "",
                updatedAt = "",
                v = 0,
                status = ""
            )

            HistorySuccessScreen(listOf(review, review, review, review))
        }
    }
}


@Composable
fun HistoryScreenControl(
    historyState: UiState<List<RemoteReviewHistoryResponse>>,
    setEvent: (HistoryEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        setEvent(HistoryEvent.FetchHistory)
    }

    AppScreen {
        when (historyState) {

            is UiState.Idle -> {
                setEvent(HistoryEvent.FetchHistory)
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                HistorySuccessScreen(history = historyState.data)
            }

            is UiState.Failed -> {
                AppFailureScreen(
                    text = historyState.message,
                    onCancel = { },
                    onTryAgain = { setEvent(HistoryEvent.FetchHistory) }
                )
            }
        }
    }
}


@Composable
fun HistorySuccessScreen(history: List<RemoteReviewHistoryResponse>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(history.size) {
            ReviewDataUI(
                title = history[it].createdFor.name,
                rating = history[it].rating,
                description = history[it].feedback,
                photoUrl = history[it].createdFor.photoUrl ?: ""
            )
        }
    }
}