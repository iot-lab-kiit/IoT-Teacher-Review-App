package `in`.iot.lab.teacherreview.feature_authentication.data.data_source.remote

import `in`.iot.lab.teacherreview.core.utils.Constants.ENDPOINT_AUTHENTICATION
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.data.models.UserAuthentication
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This is the Retrofit Interface which contains all the Server calls inside the APP which can be
 * done. These are only the Interfaces the real implementation is done by the Retrofit Library
 *
 * @property postLoginRequest this function requests the Server to check whether the UserData
 * matches the Current Database and help in Login inside the App
 */
interface RetrofitApi {

    // This calls the Api and posts the Login Request to the Server for Authentication
    @POST(ENDPOINT_AUTHENTICATION)
    suspend fun postLoginRequest(
        @Body postLoginData: PostLoginData
    ) : retrofit2.Response<UserAuthentication>
}