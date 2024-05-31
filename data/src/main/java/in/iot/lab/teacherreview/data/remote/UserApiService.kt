package `in`.iot.lab.teacherreview.data.remote

import `in`.iot.lab.network.data.CustomResponse
import `in`.iot.lab.teacherreview.domain.models.common.AccessTokenBody
import `in`.iot.lab.teacherreview.domain.models.review.PostReviewBody
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse
import `in`.iot.lab.teacherreview.utils.Constants
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Retrofit Interface defining the api calls for the User Schema.
 */
interface UserApiService {


    /**
     * This function sends the login access token from firebase to the server for creating a new
     * user data if the user is not present in the database.
     *
     * @param accessTokenBody This contains the access token from firebase.
     */
    @POST(Constants.LOGIN_AUTH_ENDPOINT)
    suspend fun loginUser(
        @Body accessTokenBody: AccessTokenBody
    ): CustomResponse<Unit>


    /**
     * This function is used to fetch the data of the user.
     *
     * @param accessTokenBody This is the token for the Authorization from the Firebase.
     */
    @POST(Constants.USER_FETCH_ENDPOINT)
    suspend fun getUserData(
        @Body accessTokenBody: AccessTokenBody
    ): CustomResponse<RemoteUser>


    /**
     * This function is used to delete a user from the Database.
     *
     * @param authToken This is the token for the Authorization from the Firebase.
     * @param userUid This is the user's Uid for which the [RemoteUser] data will be deleted.
     */
    @DELETE(Constants.USER_DELETE_ENDPOINT)
    suspend fun deleteUserData(
        @Header("Authorization") authToken: String,
        @Path("id") userUid: String
    ): CustomResponse<Unit>


    /**
     * This function fetches the user's review history from the server.
     *
     * @param authToken This is the token for the Authorization from the Firebase.
     * @param userUid This is the user's Uid for which the review history will be fetched.
     * @param limit This is the limit for the number of reviews to be fetched.
     * @param skip This is the skip for the number of reviews to be skipped.
     */
    @GET(Constants.USER_REVIEW_HISTORY_ENDPOINT)
    suspend fun getReviewHistory(
        @Header("Authorization") authToken: String,
        @Path("id") userUid: String,
        @Query("limit") limit: Int,
        @Query("page") skip: Int
    ): CustomResponse<List<RemoteReviewHistoryResponse>>


    @POST(Constants.USER_POST_REVIEW_ENDPOINT)
    suspend fun postUserReview(
        @Header("Authorization") authToken: String,
        @Body postData: PostReviewBody
    ): CustomResponse<Unit>


    @DELETE(Constants.USER_DELETE_REVIEW_ENDPOINT)
    suspend fun deleteUserReview(
        @Header("Authorization") authToken: String,
        @Path("id") reviewId: String
    ): CustomResponse<Unit>
}