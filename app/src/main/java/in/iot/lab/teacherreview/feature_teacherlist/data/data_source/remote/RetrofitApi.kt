package `in`.iot.lab.teacherreview.feature_teacherlist.data.data_source.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.FacultiesData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewPostData
import retrofit2.Response
import retrofit2.http.*

/**
 * This Interface contains all the Functions and calls that can be done on the API call
 *
 * @property getTeacherList This calls the API and fetches all the Teachers there in the Database
 * @property getIndividualTeacherReviews This calls the API and fetches detailed Reviews of
 * a Teacher and all about him in the database
 * @property getStudentReviewHistory This calls the API and fetches the particular Student
 * Review History
 * @property postTeacherReviews This calls the API and posts the Review Data to the Database
 *
 */
interface RetrofitApi {

    // This calls the API and fetches all the Teachers there in the Database
    @GET(Constants.TEACHER_LIST_ENDPOINT)
    suspend fun getTeacherList(
        @Query("${"$"}limit") limitValue: Int,
        @Query("name[${"$"}search]") facultyName: String?
    ): retrofit2.Response<FacultiesData>

    // This calls the API and fetches detailed Reviews of a Teacher and all about him in the database
    @GET("reviews?${"$"}populate=faculty&${"$"}populate=createdBy")
    suspend fun getIndividualTeacherReviews(
        @Query("faculty") facultyId: String,
        @Query("${"$"}limit") limitValue: Int
    ): retrofit2.Response<ReviewData>

    // This calls the API and fetches the particular Student Review History
    @GET("reviews?${"$"}populate=faculty&${"$"}populate=createdBy")
    suspend fun getStudentReviewHistory(
        @Query("createdBy") studentId: String,
        @Query("${"$"}limit") limitValue: Int
    ): retrofit2.Response<ReviewData>

    // This calls the API and posts the Review Data to the Database
    @POST(Constants.POST_TEACHER_REVIEW_ENDPOINT)
    suspend fun postTeacherReviews(
        @Body post: ReviewPostData
    ): retrofit2.Response<ReviewPostData>
}