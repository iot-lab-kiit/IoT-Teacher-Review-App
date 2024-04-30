package `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.core.data.local.UserPreferences
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.IndividualReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.ReviewRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.action.HistoryActions
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.HistoryScreenControl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
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

    private var _historyScreenPagingFlow: MutableStateFlow<PagingData<IndividualReviewData>> =
        MutableStateFlow(value = PagingData.empty())
    val historyScreenPagingFlow: StateFlow<PagingData<IndividualReviewData>> = _historyScreenPagingFlow

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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Reset the Paging Flow
                _historyScreenPagingFlow.value = PagingData.empty()

                reviewRepository.getStudentsReviewHistory(
                    studentId = userId
                )
                    .getOrThrow()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .collect {
                        _historyScreenPagingFlow.value = it
                    }
            } catch (e: Exception) {
                e.printStackTrace()
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