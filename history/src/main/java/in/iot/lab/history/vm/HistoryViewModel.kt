package `in`.iot.lab.history.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    private val _historyState: MutableStateFlow<UiState<List<RemoteReviewHistoryResponse>>> =
        MutableStateFlow(UiState.Idle)
    val historyState = _historyState.asStateFlow()


    private fun getHistory() {
        viewModelScope.launch {
            repo.getReviewHistory().collect {
                _historyState.value = it.toUiState()
            }
        }
    }


    fun uiListener(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.FetchHistory -> getHistory()
        }
    }
}