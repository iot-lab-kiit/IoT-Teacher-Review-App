package `in`.iot.lab.teacherreview.data.remote


import `in`.iot.lab.teacherreview.domain.models.common.AccessTokenBody
import `in`.iot.lab.teacherreview.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApiService {


    /**
     * This function sends the login access token from firebase to the server for creating a new
     * user data if the user is not present in the database.
     *
     * @param accessTokenBody This contains the access token from firebase.
     */
    @POST(Constants.LOGIN_AUTH_ENDPOINT)
    suspend fun loginUser(
        @Body accessTokenBody: AccessTokenBody
    ): Response<Unit>
}