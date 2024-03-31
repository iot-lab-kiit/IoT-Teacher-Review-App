package `in`.iot.lab.teacherreview.feature_teacherlist.data.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewPostData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ReviewsApi {
    @GET("reviews?${"$"}populate=faculty&${"$"}populate=createdBy")
    suspend fun getIndividualTeacherReviews(
        @Header("Authorization") token: String,
        @Query("faculty") facultyId: String,
        @Query("${"$"}limit") limitValue: Int
    ): Response<ReviewData>


    @GET("reviews?${"$"}populate=faculty&${"$"}populate=createdBy")
    suspend fun getStudentReviewHistory(
        @Header("Authorization") token: String,
        @Query("createdBy") studentId: String,
        @Query("${"$"}limit") limitValue: Int
    ): Response<ReviewData>


    @POST(Constants.POST_TEACHER_REVIEW_ENDPOINT)
    suspend fun postTeacherReviews(
        @Header("Authorization") token: String,
        @Body post: ReviewPostData
    ): Response<ReviewPostData>

    // TODO: ADD MORE METHODS HERE GET,DELETE,PUT,PATCH
}