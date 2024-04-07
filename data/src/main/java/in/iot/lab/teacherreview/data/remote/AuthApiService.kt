package `in`.iot.lab.teacherreview.data.remote

import `in`.iot.lab.teacherreview.utils.Constants
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import `in`.iot.lab.teacherreview.domain.models.auth.RemoteUserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST(Constants.LOGIN_AUTH_ENDPOINT)
    suspend fun loginUser(
        @Body userData: RemoteUser
    ): Response<RemoteUserLoginResponse>
}