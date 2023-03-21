package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.FacultiesData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.HomeScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * This is the ViewModel Class for the [HomeScreen]
 *
 * @property myRepository This is the Repository Variable
 * @property teacherListApiCallState This is the State of the API Call
 * @property teacherList This is the list of teachers fetched from the Server
 * @property getTeacherList This function gets the Teacher List from the Server
 */
class TeacherListViewModel : ViewModel() {

    // Repository Variable
    private val myRepository = Repository()

    // Api Call state variable
    var teacherListApiCallState: TeacherListApiCallState by mutableStateOf(TeacherListApiCallState.Initialized)
        private set

    // Teacher List Variable Which contains the List of Teacher to be shown to the User
    var teacherList: FacultiesData? by mutableStateOf(null)
        private set

    init {

        // Fetching the Data once to get Started
        getTeacherList()
    }

    // This function fetches the List of Teachers
    fun getTeacherList() {

        // Setting the Current State to Loading Before Starting to Fetch Data
        teacherListApiCallState = TeacherListApiCallState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            teacherListApiCallState = try {

                // Response from the Server
                val response = myRepository.getTeacherList(limitValue = 10)

                // If the Response is Success then Storing the Data of the Teachers to the variable
                if (response is TeacherListApiCallState.Success)
                    teacherList = response.facultyData

                // The Api State is given from the above function so we need to just assign it to api Call Variable
                response
            } catch (_: ConnectException) {
                TeacherListApiCallState.Failure("No Internet Connection")
            }
        }
    }
}