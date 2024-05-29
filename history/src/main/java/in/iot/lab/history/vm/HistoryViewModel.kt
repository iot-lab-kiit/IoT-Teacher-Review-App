package `in`.iot.lab.history.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.history.view.event.HistoryEvent
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


    fun uiListener(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.FetchHistory -> getHistory()
        }
    }
}