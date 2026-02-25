package `in`.iot.lab.review.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkUtil.toUiState
import `in`.iot.lab.review.view.events.FacultyEvent
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.kritique.domain.models.review.PostReviewBody
import `in`.iot.lab.kritique.domain.models.review.RemoteFacultyReview
import `in`.iot.lab.kritique.domain.models.review.RemoteReviewHistoryResponse
import `in`.iot.lab.kritique.domain.repository.FacultyRepo
import `in`.iot.lab.kritique.domain.repository.UserRepo
import `in`.iot.lab.network.state.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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


    private fun getFacultyByName(name: String) {
        viewModelScope.launch {
            facultyRepo.getTeacherByName(name)
                .cachedIn(viewModelScope)
                .collect {
                    _facultyList.value = it
                }
        }
    }

    private var selectedFacultyId: String = "Default Faculty Id"

    private fun setFaculty(id: String) {
        selectedFacultyId = id
    }


    private val _facultyData: MutableStateFlow<UiState<RemoteFaculty>> =
        MutableStateFlow(UiState.Idle)
    val facultyData = _facultyData.asStateFlow()

    private fun getFacultyData() {
        viewModelScope.launch {
            facultyRepo.getFacultyById(selectedFacultyId).collect {
                _facultyData.value = it.toUiState()
            }
        }
    }


    private val _reviewList: MutableStateFlow<PagingData<RemoteFacultyReview>> =
        MutableStateFlow(PagingData.empty())
    val reviewList = _reviewList.asStateFlow()


//    private fun getFacultyReview() {
//        viewModelScope.launch {
//            facultyRepo.getFacultyReviewData(selectedFacultyId).collect {
//                _reviewList.value = it
//            }
//        }
//    }

    private fun getFacultyReview() {
        viewModelScope.launch {
            facultyRepo.getFacultyReviewData(selectedFacultyId)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _reviewList.value = it
                }
        }
    }

    private val _reviewSubmitState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Idle)
    val reviewSubmitState = _reviewSubmitState.asStateFlow()


    private val _editingReview =
        MutableStateFlow<RemoteReviewHistoryResponse?>(null)
    val editingReview: StateFlow<RemoteReviewHistoryResponse?> =
        _editingReview.asStateFlow()
    fun startEditingReview(review: RemoteReviewHistoryResponse) {
        _editingReview.value = review
        selectedFacultyId = review.createdFor.id
    }
    fun clearEditingReview() {
        _editingReview.value = null
    }
    private fun submitReview(rating: Double, feedback: String) {
        viewModelScope.launch {

            val editing = _editingReview.value

            if (editing != null) {

                // STEP 1: Delete old review
                userRepo.deleteUserReview(editing.id).collect { deleteState ->

                    if (deleteState is ResponseState.Success) {

                        // STEP 2: Post updated review
                        userRepo.postUserReview(
                            PostReviewBody(
                                createdBy = userRepo.getUserUid(),
                                createdFor = selectedFacultyId,
                                rating = rating,
                                feedback = feedback
                            )
                        ).collect { postState ->

                            _reviewSubmitState.value = postState.toUiState()

                            if (postState is ResponseState.Success) {
                                clearEditingReview()
                                // Refresh faculty review list
                                getFacultyReview()
                                // Refresh faculty data (avg rating etc)
                                getFacultyData()
                            }
                        }

                    } else {
                        _reviewSubmitState.value = deleteState.toUiState()
                    }
                }

            } else {

                // NORMAL CREATE
                userRepo.postUserReview(
                    PostReviewBody(
                        createdBy = userRepo.getUserUid(),
                        createdFor = selectedFacultyId,
                        rating = rating,
                        feedback = feedback
                    )
                ).collect {
                    _reviewSubmitState.value = it.toUiState()
                }
            }
        }
    }


    fun uiListener(event: FacultyEvent) {
        when (event) {
            is FacultyEvent.FetchFacultyList -> getFacultyList()
            is FacultyEvent.FetchFacultyByName -> getFacultyByName(event.name)
            is FacultyEvent.FacultySelected -> setFaculty(event.facultyId)
            is FacultyEvent.GetFacultyDetails -> {
                getFacultyData()
                getFacultyReview()
            }

            is FacultyEvent.SubmitReview -> submitReview(event.rating, event.feedback)
            is FacultyEvent.ResetSubmitState -> _reviewSubmitState.value = UiState.Idle
        }
    }
}