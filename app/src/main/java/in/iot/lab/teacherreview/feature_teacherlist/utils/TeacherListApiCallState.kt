package `in`.iot.lab.teacherreview.feature_teacherlist.utils

import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Faculty
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState.Failure
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState.Initialized
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState.Loading
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState.Success

/**
 * This sealed Class contains all the States of the Teacher List Request of a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in fetching Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class TeacherListApiCallState {
    object Initialized : TeacherListApiCallState()
    object Loading : TeacherListApiCallState()
    class Success(val facultyData: List<Faculty>) : TeacherListApiCallState()
    class Failure(val errorMessage: String) : TeacherListApiCallState()
}