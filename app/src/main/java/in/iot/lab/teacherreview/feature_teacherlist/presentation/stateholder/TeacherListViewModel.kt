package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HomeScreenControl
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.IndividualTeacherReviewApiCall
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * This is the ViewModel Class for the [HomeScreenControl]
 *
 * @property myRepository This is the Repository Variable
 * @property teacherListApiCallState This is the State of the API Call
 * @property selectedTeacher This is the Selected teacher by the User
 *
 *
 * @property getTeacherList This function gets the Teacher List from the Server
 * @property addTeacherForNextScreen This function initialises the Teacher variable for
 * the next Screen
 */
class TeacherListViewModel : ViewModel() {

    // Repository Variable
    private val myRepository = Repository()

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
            teacherListApiCallState = try {

                // Response from the Server
                myRepository.getTeacherList(limitValue = 10)

            } catch (_: ConnectException) {
                TeacherListApiCallState.Failure("No Internet Connection")
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
            individualTeacherReviewApiCall = try {

                // Response from the Server
                myRepository.getIndividualTeacherReviews(
                    limitValue = 10,
                    facultyId = facultyId
                )

            } catch (_: ConnectException) {
                IndividualTeacherReviewApiCall.Failure("No Internet Connection")
            }
        }
    }
}