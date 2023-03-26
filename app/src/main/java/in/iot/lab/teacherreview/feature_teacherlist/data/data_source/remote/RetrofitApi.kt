package `in`.iot.lab.teacherreview.feature_teacherlist.data.data_source.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.FacultiesData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * This Interface contains all the Functions and calls that can be done on the API call
 *
 * @property getTeacherList This calls the API and fetches all the Teachers there in the Database
 * @property getIndividualTeacherReviews This calls the API and fetches detailed Reviews of
 * a Teacher and all about him in the database
 *
 */
interface RetrofitApi {

    // This calls the API and fetches all the Teachers there in the Database
    @GET(Constants.TEACHER_LIST_ENDPOINT)
    suspend fun getTeacherList(
        @Header("Authorization") token: String = Constants.ACCESS_TOKEN,
        @Query("${"$"}limit") limitValue: Int,
        @Query("name[${"$"}search]") facultyName: String?
    ): retrofit2.Response<FacultiesData>

    // This calls the API and fetches detailed Reviews of a Teacher and all about him in the database
    @GET("reviews?${"$"}populate=faculty&${"$"}populate=subject&${"$"}populate=createdBy")
    suspend fun getIndividualTeacherReviews(
        @Header("Authorization") token: String = Constants.ACCESS_TOKEN,
        @Query("faculty") facultyId: String,
        @Query("${"$"}limit") limitValue: Int
    ): retrofit2.Response<ReviewData>
}