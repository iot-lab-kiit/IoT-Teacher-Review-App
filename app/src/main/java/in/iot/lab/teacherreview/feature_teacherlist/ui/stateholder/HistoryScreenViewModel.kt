package `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.core.data.local.UserPreferences
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.TeacherRepositoryImpl
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.ReviewRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.action.HistoryActions
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.HistoryScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.GetHistoryApiCallState
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

/**
 * This is the ViewModel of the [HistoryScreenControl] Screen
 *
 * @property myTeacherRepositoryImpl Contains the Repository Variable
 * @property getHistoryApiCallState  This holds the state of the History Api Request
 * @property getStudentReviewHistory This function fetches the Student's History Reviews
 */
@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    userPreferences: UserPreferences,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val userIdFlow = userPreferences.userId
    private var userId = ""

    // This holds the state of the History Api Request
    var getHistoryApiCallState: GetHistoryApiCallState by mutableStateOf(
        GetHistoryApiCallState.Initialized
    )
        private set

    init {
        viewModelScope.launch {
            userIdFlow.collect {
                if (it != null) {
                    userId = it
                }
            }
        }
    }

    // This function fetches the Student's History Reviews
    fun getStudentReviewHistory() {

        // Setting the Current State to Loading Before Starting to Fetch Data
        getHistoryApiCallState = GetHistoryApiCallState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            try {

                // Response from the Server
                reviewRepository.getStudentsReviewHistory(
                    limitValue = 10,
                    studentId = userId
                ).onSuccess {
                    getHistoryApiCallState = GetHistoryApiCallState.Success(it)
                }.onFailure {
                    getHistoryApiCallState =
                        GetHistoryApiCallState.Failure(it.message.toString())
                }

            } catch (_: ConnectException) {
                getHistoryApiCallState = GetHistoryApiCallState.Failure("No Internet Connection")
            }
        }
    }

    fun historyAction(historyActions: HistoryActions) {
        when (historyActions) {
            is HistoryActions.GetStudentReviewHistory -> getStudentReviewHistory()
        }
    }
    // Testing
}