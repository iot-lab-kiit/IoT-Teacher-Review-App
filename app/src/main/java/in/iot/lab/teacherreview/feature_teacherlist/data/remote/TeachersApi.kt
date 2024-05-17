package `in`.iot.lab.teacherreview.feature_teacherlist.data.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Faculty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TeachersApi {
    @GET(Constants.TEACHER_LIST_ENDPOINT)
    suspend fun getTeacherList(
        @Header("Authorization") token: String,
        @Query("${"$"}limit") limitValue: Int,
        @Query("name[${"$"}search]") facultyName: String?
    ): Response<List<Faculty>>
}