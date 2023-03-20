package `in`.iot.lab.teacherreview.feature_authentication.data.data_source.remote

import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_authentication.data.models.AuthenticationResponse
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostSignupData
import `in`.iot.lab.teacherreview.feature_authentication.data.models.UserData
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This is the Retrofit Interface which contains all the Server calls inside the APP which can be
 * done. These are only the Interfaces the real implementation is done by the Retrofit Library
 *
 * @property postLoginRequest this function requests the Server to check whether the UserData
 * matches the Current Database and help in Login inside the App
 * @property postSignupRequest This calls the API and post a new User Details to the Server
 * to create a new User
 */
interface RetrofitApi {

    // This calls the Api and posts the Login Request to the Server for Authentication
    @POST(Constants.LOGIN_AUTHENTICATION_ENDPOINT)
    suspend fun postLoginRequest(
        @Body postLoginData: PostLoginData
    ): retrofit2.Response<AuthenticationResponse>

    // This calls the API and post a new User Details to the Server to create a new User
    @POST(Constants.SIGNUP_POST_ENDPOINT)
    suspend fun postSignupRequest(
        @Body postSignupData: PostSignupData
    ): retrofit2.Response<UserData>
}