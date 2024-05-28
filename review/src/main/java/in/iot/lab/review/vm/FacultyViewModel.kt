package `in`.iot.lab.review.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse
import `in`.iot.lab.teacherreview.domain.repository.FacultyRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FacultyViewModel @Inject constructor(
    private val repo: FacultyRepo
) : ViewModel() {

    private val _facultyList: MutableStateFlow<UiState<List<RemoteFaculty>>> =
        MutableStateFlow(UiState.Idle)
    val facultyList = _facultyList.asStateFlow()


    private fun getFacultyList() {
        viewModelScope.launch {
            repo.getTeacherList().collect {
                _facultyList.value = it.toUiState()
            }
        }
    }

    private var selectedFaculty: String = "Default Value"

    private fun setFaculty(id: String) {
        selectedFaculty = id
    }


    private val _facultyDetails: MutableStateFlow<UiState<RemoteFacultyReviewResponse>> =
        MutableStateFlow(UiState.Idle)
    val facultyDetails = _facultyDetails.asStateFlow()


    private fun getFacultyReview() {
        viewModelScope.launch {
            repo.getFacultyReviewData(selectedFaculty).collect {
                _facultyDetails.value = it.toUiState()
            }
        }
    }


    fun uiListener(event: FacultyEvent) {
        when (event) {
            is FacultyEvent.FetchFacultyList -> getFacultyList()
            is FacultyEvent.FacultySelected -> setFaculty(event.facultyId)
            is FacultyEvent.GetFacultyDetails -> getFacultyReview()
        }
    }
}