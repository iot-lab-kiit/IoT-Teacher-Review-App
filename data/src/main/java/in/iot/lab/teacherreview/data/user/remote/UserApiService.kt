package `in`.iot.lab.teacherreview.data.user.remote

import `in`.iot.lab.teacherreview.common.Constants
import `in`.iot.lab.teacherreview.common.model.RemoteUser
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


/**
 * Retrofit Interface defining the api calls for the User Schema.
 */
interface UserApiService {


    /**
     * This function is used to fetch the data of the user.
     *
     * @param authToken This is the token for the Authorization from the Firebase.
     * @param userUid This is the user uid for which the data needs to be fetched.
     */
    @GET(Constants.USER_FETCH_ENDPOINT)
    suspend fun getUserData(
        @Header("Authorization") authToken: String,
        @Path("userUid") userUid: String
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
        @Path("userUid") userUid: String
    ): Response<Unit>
}