package `in`.iot.lab.teacherreview.data.auth.remote

import `in`.iot.lab.teacherreview.common.Constants
import `in`.iot.lab.teacherreview.common.model.RemoteUser
import `in`.iot.lab.teacherreview.data.auth.model.RemoteUserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST(Constants.LOGIN_AUTH_ENDPOINT)
    suspend fun loginUser(
        @Body userData: RemoteUser
    ): Response<RemoteUserLoginResponse>
}