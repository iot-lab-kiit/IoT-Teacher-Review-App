package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.action.HistoryActions
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components.ReviewCardItem
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.GetHistoryApiCallState

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        HistoryScreenLoading()
    }
}

// This is the Preview function of the Screen when Success
@Preview("Light")
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewSuccess() {
    CustomAppTheme {
        HistoryScreenSuccess(
            ReviewData()
        )
    }
}

// This is the Preview function of the Screen when Failure
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewFailure() {
    CustomAppTheme {
        HistoryScreenFailure(
            onRetryClick = {}
        )
    }
}

/**
 * This function decides which screen to show according to the different States
 *
 * @param modifier This is the default Modifier passed from the Parent Class
 */
@Composable
fun HistoryScreenControl(
    modifier: Modifier = Modifier,
    historyActions: (HistoryActions)->Unit,
    getHistoryApiCallState: GetHistoryApiCallState,
    userIdFlow:String
) {

    // ViewModel Variable
    //val myViewModel: HistoryScreenViewModel = hiltViewModel()

    // Redirecting to respective Screens
    when (getHistoryApiCallState) {
        is GetHistoryApiCallState.Initialized -> {
            historyActions(HistoryActions.GetStudentReviewHistory)
        }
        is GetHistoryApiCallState.Loading -> {
            HistoryScreenLoading(
                modifier = modifier
            )
        }
        is GetHistoryApiCallState.Success -> {
            HistoryScreenSuccess(
                reviewData = getHistoryApiCallState.reviewData
            )
        }
        else -> {
            HistoryScreenFailure {
                historyActions(HistoryActions.GetStudentReviewHistory)
            }
        }
    }
}

// This state is shown when the Screen is Loading
@Composable
fun HistoryScreenLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

// This function is shown when the API call request is a Success
@Composable
fun HistoryScreenSuccess(
    reviewData: ReviewData
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {

        items(reviewData.individualReviewData!!.size) { itemCount ->

            // Current Review
            val reviewItem = reviewData.individualReviewData[itemCount]

            // Showing the Review in the reviewCard UI
            ReviewCardItem(
                createdBy = reviewItem.createdBy,
                review = reviewItem.review!!
            )

            // Spacer of Height 16 dp
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


// This Screen is Shown when the Request is a Failure
@Composable
fun HistoryScreenFailure(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onRetryClick
        ) {
            Text(
                text = stringResource(R.string.failed_to_load_tap_to_retry),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}