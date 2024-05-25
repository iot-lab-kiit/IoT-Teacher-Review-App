package `in`.iot.lab.teacherreview.data.remote

import `in`.iot.lab.teacherreview.domain.models.common.AccessTokenBody
import `in`.iot.lab.teacherreview.utils.Constants
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Retrofit Interface defining the api calls for the User Schema.
 */
interface UserApiService {


    /**
     * This function is used to fetch the data of the user.
     *
     * @param accessTokenBody This is the token for the Authorization from the Firebase.
     */
    @POST(Constants.USER_FETCH_ENDPOINT)
    suspend fun getUserData(
        @Body accessTokenBody: AccessTokenBody
    ): Response<RemoteUser>


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
    ): Response<Unit>
}