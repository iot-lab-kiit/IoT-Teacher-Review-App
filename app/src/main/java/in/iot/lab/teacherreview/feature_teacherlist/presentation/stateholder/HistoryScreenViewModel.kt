package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HistoryScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.GetHistoryApiCallState
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * This is the ViewModel of the [HistoryScreenControl] Screen
 *
 * @property myRepository Contains the Repository Variable
 * @property getHistoryApiCallState  This holds the state of the History Api Request
 * @property getStudentReviewHistory This function fetches the Student's History Reviews
 */
class HistoryScreenViewModel : ViewModel() {

    // Repository Variable
    private val myRepository: Repository = Repository()

    // This holds the state of the History Api Request
    var getHistoryApiCallState: GetHistoryApiCallState by mutableStateOf(
        GetHistoryApiCallState.Initialized
    )
        private set

    // This function fetches the Student's History Reviews
    fun getStudentReviewHistory() {

        // Setting the Current State to Loading Before Starting to Fetch Data
        getHistoryApiCallState = GetHistoryApiCallState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            getHistoryApiCallState = try {

                // Response from the Server
                myRepository.getStudentReviewHistory(
                    limitValue = 10,
                )

            } catch (_: ConnectException) {
                GetHistoryApiCallState.Failure("No Internet Connection")
            }
        }
    }
}