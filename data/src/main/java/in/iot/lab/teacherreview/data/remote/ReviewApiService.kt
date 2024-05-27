package `in`.iot.lab.teacherreview.data.remote

import `in`.iot.lab.teacherreview.utils.Constants
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReview
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApiService {

    @GET(Constants.FACULTY_REVIEW_FETCH_ENDPOINT)
    suspend fun getFacultyReviewData(
        @Header("Authorization") authToken: String,
        @Path("teacherId") teacherId: String
    ): Response<RemoteFacultyReviewResponse>

    @POST(Constants.USER_POST_REVIEW_ENDPOINT)
    suspend fun postUserReview(
        @Header("Authorization") authToken: String,
        @Body postData: RemoteReview
    ): Response<Unit>
}