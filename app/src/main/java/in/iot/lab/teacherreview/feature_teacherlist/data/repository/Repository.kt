package `in`.iot.lab.teacherreview.feature_teacherlist.data.repository

import `in`.iot.lab.teacherreview.feature_teacherlist.data.data_source.remote.RetrofitInstance
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState

/**
 * This is the repository which is going to control flow of every data related to
 * feature_teacher list module
 *
 * @property getTeacherList This Function calls the Server and fetches the Teacher List to
 * be shown to the user
 */
class Repository {

    // This Function calls the Server and fetches the Teacher List to be shown to the user
    suspend fun getTeacherList(limitValue: Int, facultyName: String? = null): TeacherListApiCallState {

        // This is the Response from the Server
        val response = RetrofitInstance.apiInstance.getTeacherList(
            limitValue = limitValue,
            facultyName = facultyName
        )

        // Checking if the Response is successful or a Failure
        return if (response.isSuccessful)
            TeacherListApiCallState.Success(response.body()!!)
        else
            TeacherListApiCallState.Failure(errorMessage = "Error Connecting to the Server")
    }
}