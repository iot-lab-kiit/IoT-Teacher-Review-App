package `in`.iot.lab.teacherreview.feature_teacherlist.data.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Faculty
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewsApi {
    // TODO: expose the sort query parameter as function parameter to make it more flexible
    @GET("${Constants.REVIEWS_ENDPOINT}/{facultyId}")
    suspend fun getIndividualTeacherReviews(
        @Header("Authorization") token: String,
        @Path("facultyId") facultyId: String,
        @Query("limit") limitValue: Int,
        @Query("skip") skip: Int = 0
    ): Response<Faculty>


    @GET(Constants.REVIEWS_ENDPOINT)
    suspend fun getStudentReviewHistory(
        @Header("Authorization") token: String,
        @Query("createdBy") studentId: String,
        @Query("limit") limitValue: Int,
        @Query("skip") skip: Int = 0
    ): Response<List<Review>>


    @POST(Constants.REVIEWS_ENDPOINT)
    suspend fun postTeacherReviews(
        @Header("Authorization") token: String,
        @Body post: ReviewPostData
    ): Response<ReviewResponse>

    // TODO: ADD MORE METHODS HERE GET,DELETE,PUT,PATCH

    @DELETE("${Constants.REVIEWS_ENDPOINT}/{reviewId}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: String
    ): Response<ReviewResponse>
}