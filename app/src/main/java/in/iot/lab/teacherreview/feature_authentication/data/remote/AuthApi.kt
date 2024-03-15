package `in`.iot.lab.teacherreview.feature_authentication.data.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.AuthResponse
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.PostLoginData
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This is the Retrofit Interface which contains all the Server calls inside the APP which can be
 * done. These are only the Interfaces the real implementation is done by the Retrofit Library
 *
 * @property postLoginRequest this function requests the Server to authenticate the User and
 * to create/update a new User
 */
interface AuthApi {

    // This calls the Api and posts the Login Request to the Server for Authentication
    @POST(Constants.LOGIN_AUTHENTICATION_ENDPOINT)
    suspend fun postLoginRequest(
        @Body postLoginData: PostLoginData
    ): retrofit2.Response<AuthResponse>
}