package `in`.iot.lab.teacherreview.feature_authentication.data.repository

import `in`.iot.lab.teacherreview.feature_authentication.data.data_source.remote.RetrofitInstance.apiInstance
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostLoginData
import `in`.iot.lab.teacherreview.feature_authentication.data.models.PostSignupData
import `in`.iot.lab.teacherreview.feature_authentication.util.LoginState
import `in`.iot.lab.teacherreview.feature_authentication.util.RegistrationState

/**
 * This class basically is responsible for calling for the Data and returning the Data needed by
 * the app
 *
 * @property postLoginRequest This calls the API and posts the User Authentication request
 * @property postSignupRequest This calls the API and posts the new User Request to the Server
 */
class Repository {

    // This calls the API and posts the User Authentication request
    suspend fun postLoginRequest(postLoginData: PostLoginData): LoginState {
        val response = apiInstance.postLoginRequest(postLoginData)

        return if (response.isSuccessful)
            LoginState.Success(response.body()!!)
        else
            LoginState.Failure(errorMessage = "Wrong Credentials or try again")
    }

    // This calls the API and posts the new User Request to the Server
    suspend fun postSignupRequest(postSignupData: PostSignupData): RegistrationState {
        val response = apiInstance.postSignupRequest(postSignupData)

        return if (response.isSuccessful)
            RegistrationState.Success(response.body()!!)
        else
            RegistrationState.Failure(errorMessage = "Please Check the Data entered")
    }
}