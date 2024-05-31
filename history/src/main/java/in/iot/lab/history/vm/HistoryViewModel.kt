package `in`.iot.lab.history.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.history.view.event.HistoryEvent
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse
import `in`.iot.lab.teacherreview.domain.repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: UserRepo
) : ViewModel() {


    private val _history: MutableStateFlow<PagingData<RemoteReviewHistoryResponse>> =
        MutableStateFlow(PagingData.empty())
    val history = _history.asStateFlow()


    private fun getHistory() {
        viewModelScope.launch {
            repo.getReviewHistory()
                .cachedIn(viewModelScope)
                .collect {
                    _history.value = it
                }
        }
    }


    private val _deleteReviewState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Idle)
    val deleteReviewState = _deleteReviewState.asStateFlow()


    private fun deleteUserHistory(reviewId: String) {
        viewModelScope.launch {
            repo.deleteUserReview(reviewId).collect {
                _deleteReviewState.value = it.toUiState()

                if (_deleteReviewState.value is UiState.Success)
                    getHistory()
            }
        }
    }


    fun uiListener(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.FetchHistory -> getHistory()
            is HistoryEvent.RemoveReview -> deleteUserHistory(event.reviewId)
            is HistoryEvent.ResetRemoveState -> _deleteReviewState.value = UiState.Idle
        }
    }
}