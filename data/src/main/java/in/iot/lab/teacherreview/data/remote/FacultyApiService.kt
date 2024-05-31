package `in`.iot.lab.teacherreview.data.remote

import `in`.iot.lab.network.data.CustomResponse
import `in`.iot.lab.teacherreview.utils.Constants
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReview
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


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
        @Query("name") teacherName: String,
        @Query("limit") limit: Int,
        @Query("page") skip: Int
    ): CustomResponse<List<RemoteFaculty>>


    @GET(Constants.FACULTY_FETCH_ALL_ENDPOINT)
    suspend fun getFacultyList(
        @Header("authorization") authToken: String,
        @Query("limit") limit: Int,
        @Query("page") skip: Int
    ): CustomResponse<List<RemoteFaculty>>


    @GET(Constants.FACULTY_REVIEW_FETCH_ENDPOINT)
    suspend fun getFacultyReviewData(
        @Header("Authorization") authToken: String,
        @Path("id") facultyId: String,
        @Query("limit") limit: Int,
        @Query("page") skip: Int
    ): CustomResponse<List<RemoteFacultyReview>>


    @GET(Constants.FACULTY_FETCH_BY_ID_ENDPOINT)
    suspend fun getFacultyById(
        @Header("Authorization") authToken: String,
        @Path("id") facultyId: String
    ): CustomResponse<RemoteFaculty>
}