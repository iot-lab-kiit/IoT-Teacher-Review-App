package `in`.iot.lab.teacherreview.data.faculty.remote

import `in`.iot.lab.teacherreview.common.Constants
import `in`.iot.lab.teacherreview.common.model.RemoteFaculty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


/**
 * This interface is made to be implemented by the Retrofit so we can use its implementation to
 * call the server and send requests.
 *
 * @property getTeacherByName This function fetches the [RemoteFaculty] object using the name of
 * the User.
 * @property getFacultyList This function fetches all the [RemoteFaculty] object from the database.
 */
interface FacultyApiService {

    @GET(Constants.FACULTY_FETCH_BY_NAME_ENDPOINT)
    suspend fun getTeacherByName(
        @Header("authorization") authToken: String,
        @Path("teacherName") teacherName: String
    ): Response<List<RemoteFaculty>>


    @GET(Constants.FACULTY_FETCH_ALL_ENDPOINT)
    suspend fun getFacultyList(
        @Header("authorization") authToken: String
    ): Response<List<RemoteFaculty>>
}