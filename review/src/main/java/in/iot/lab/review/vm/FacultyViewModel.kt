package `in`.iot.lab.review.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.domain.models.review.PostReviewBody
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse
import `in`.iot.lab.teacherreview.domain.repository.FacultyRepo
import `in`.iot.lab.teacherreview.domain.repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FacultyViewModel @Inject constructor(
    private val facultyRepo: FacultyRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _facultyList: MutableStateFlow<PagingData<RemoteFaculty>> =
        MutableStateFlow(PagingData.empty())
    val facultyList = _facultyList.asStateFlow()


    private fun getFacultyList() {
        viewModelScope.launch {
            facultyRepo.getTeacherList()
                .cachedIn(viewModelScope)
                .collect {
                    _facultyList.value = it
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
            facultyRepo.getFacultyReviewData(selectedFaculty).collect {
                _facultyDetails.value = it.toUiState()
            }
        }
    }


    private val _reviewSubmitState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Idle)
    val reviewSubmitState = _reviewSubmitState.asStateFlow()


    private fun submitReview(rating: Double, feedback: String) {
        viewModelScope.launch {

            val postBody = PostReviewBody(
                createdBy = userRepo.getUserUid(),
                createdFor = selectedFaculty,
                rating = rating,
                feedback = feedback
            )

            userRepo.postUserReview(postBody).collect {
                _reviewSubmitState.value = it.toUiState()
            }
        }
    }

    fun uiListener(event: FacultyEvent) {
        when (event) {
            is FacultyEvent.FetchFacultyList -> getFacultyList()
            is FacultyEvent.FacultySelected -> setFaculty(event.facultyId)
            is FacultyEvent.GetFacultyDetails -> getFacultyReview()
            is FacultyEvent.SubmitReview -> submitReview(event.rating, event.feedback)
            is FacultyEvent.ResetSubmitState -> _reviewSubmitState.value = UiState.Idle
        }
    }
}