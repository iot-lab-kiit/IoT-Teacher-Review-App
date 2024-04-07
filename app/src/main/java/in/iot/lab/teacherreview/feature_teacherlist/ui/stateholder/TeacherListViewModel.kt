package `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.core.data.local.UserPreferences
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.IndividualReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.ReviewRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.TeachersRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.HomeScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.state_action.TeacherListAction
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

/**
 * This is the ViewModel Class for the [HomeScreenControl]
 *
 * @property myTeacherRepositoryImpl This is the Repository Variable
 * @property teacherListApiCallState This is the State of the API Call
 * @property selectedTeacher This is the Selected teacher by the User
 *
 *
 * @property getTeacherList This function gets the Teacher List from the Server
 * @property addTeacherForNextScreen This function initialises the Teacher variable for
 * the next Screen
 */
@HiltViewModel
class TeacherListViewModel @Inject constructor(
    private val teachersRepository: TeachersRepository,
    private val reviewRepository: ReviewRepository,
    userPreferences: UserPreferences
) : ViewModel() {

    // Api Call state variable which also contains the data fetched from the API Call
    var teacherListApiCallState: TeacherListApiCallState by mutableStateOf(TeacherListApiCallState.Initialized)
        private set

    var selectedTeacher: IndividualFacultyData? = null
        private set

    private val _currentUserId = userPreferences.userId
    val currentUserId: MutableStateFlow<String?> = MutableStateFlow(null)

    private var _pagingFlow: MutableStateFlow<PagingData<IndividualReviewData>> =
        MutableStateFlow(value = PagingData.empty())
    val pagingFlow: StateFlow<PagingData<IndividualReviewData>> = _pagingFlow

    init {
        viewModelScope.launch {
            _currentUserId.collect {
                currentUserId.value = it
            }
        }
    }

    // This function fetches the List of Teachers
    private fun getTeacherList(
        searchQuery: String? = null
    ) {

        // Setting the Current State to Loading Before Starting to Fetch Data
        teacherListApiCallState = TeacherListApiCallState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            try {

                // Response from the Server
                teachersRepository.getAllTeachers(
                    limitValue = 10,
                    searchQuery = searchQuery
                ).onSuccess {
                    teacherListApiCallState = TeacherListApiCallState.Success(it)
                }.onFailure {
                    teacherListApiCallState =
                        TeacherListApiCallState.Failure(it.message ?: "Unknown Error")
                }

            } catch (_: ConnectException) {
                teacherListApiCallState = TeacherListApiCallState.Failure("No Internet Connection")
            }
        }
    }

    // This function initialises the Teacher variable for the next Screen
    private fun addTeacherForNextScreen(teacher: IndividualFacultyData) {
        selectedTeacher = teacher
    }

    // This function fetches the List of Teachers
    fun getIndividualTeacherReviews(facultyId: String = selectedTeacher!!._id) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Reset the Paging Flow
                _pagingFlow.value = PagingData.empty()

                reviewRepository.getTeacherReviews(
                    facultyId = facultyId
                )
                    .getOrThrow()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .collect {
                        _pagingFlow.value = it
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun action(operation: TeacherListAction) {
        when (operation) {
            is TeacherListAction.GetTeacherList -> getTeacherList(operation.searchQuery)
            is TeacherListAction.AddTeacherForNextScreen -> addTeacherForNextScreen(operation.teacher)
            is TeacherListAction.GetIndividualTeacherReviews -> getIndividualTeacherReviews(
                operation.teacherId
            )
        }
    }
}