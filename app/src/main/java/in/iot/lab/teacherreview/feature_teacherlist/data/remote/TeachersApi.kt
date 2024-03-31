package `in`.iot.lab.teacherreview.feature_teacherlist.data.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.FacultiesData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewPostData
import retrofit2.Response
import retrofit2.http.*

interface TeachersApi {
    @GET(Constants.TEACHER_LIST_ENDPOINT)
    suspend fun getTeacherList(
        @Header("Authorization") token: String,
        @Query("${"$"}limit") limitValue: Int,
        @Query("name[${"$"}search]") facultyName: String?
    ): Response<FacultiesData>
}