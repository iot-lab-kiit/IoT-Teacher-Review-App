package `in`.iot.lab.teacherreview.feature_teacherlist.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.ReviewRepositoryImpl
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.TeacherRepositoryImpl
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.ReviewRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.TeachersRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.screen.HomeScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.ui.state_action.TeacherListAction
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.IndividualTeacherReviewApiCall
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState
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
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    // Api Call state variable which also contains the data fetched from the API Call
    var teacherListApiCallState: TeacherListApiCallState by mutableStateOf(TeacherListApiCallState.Initialized)
        private set

    var selectedTeacher: IndividualFacultyData? = null
        private set

    var individualTeacherReviewApiCall: IndividualTeacherReviewApiCall by mutableStateOf(
        IndividualTeacherReviewApiCall.Initialized
    )
        private set

    // This function fetches the List of Teachers
    fun getTeacherList() {

        // Setting the Current State to Loading Before Starting to Fetch Data
        teacherListApiCallState = TeacherListApiCallState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            try {

                // Response from the Server
                teachersRepository.getAllTeachers(
                    limitValue = 10,
                    // TODO: To be implemented later
                    searchQuery = null
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
    fun addTeacherForNextScreen(teacher: IndividualFacultyData) {
        selectedTeacher = teacher
    }

    // This function fetches the List of Teachers
    fun getIndividualTeacherReviews(facultyId: String = selectedTeacher!!._id) {

        // Setting the Current State to Loading Before Starting to Fetch Data
        individualTeacherReviewApiCall = IndividualTeacherReviewApiCall.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            try {

                // Response from the Server
                reviewRepository.getTeacherReviews(
                    limitValue = 50,
                    facultyId = facultyId
                ).onSuccess {
                    individualTeacherReviewApiCall = IndividualTeacherReviewApiCall.Success(it)
                }.onFailure {
                    individualTeacherReviewApiCall =
                        IndividualTeacherReviewApiCall.Failure(it.message ?: "Unknown Error")
                }

            } catch (_: ConnectException) {
                individualTeacherReviewApiCall =
                    IndividualTeacherReviewApiCall.Failure("No Internet Connection")
            }
        }
    }

    fun action(operation: TeacherListAction) {
        when (operation) {
            TeacherListAction.GetTeacherList -> getTeacherList()
            is TeacherListAction.AddTeacherForNextScreen -> addTeacherForNextScreen(operation.teacher)
            is TeacherListAction.GetIndividualTeacherReviews -> getIndividualTeacherReviews(
                operation.teacherId
            )
        }
    }
}