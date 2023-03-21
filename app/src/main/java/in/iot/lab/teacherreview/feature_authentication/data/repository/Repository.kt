package `in`.iot.lab.teacherreview.feature_authentication.data.repository

import `in`.iot.lab.teacherreview.core.utils.Constants
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

        // Login Response from the Server
        val response = apiInstance.postLoginRequest(postLoginData)

        return if (response.isSuccessful) {

            // Setting the Current AccessToken received from The server for using it later
            Constants.setAccessToken(response.body()!!.accessToken)
            LoginState.Success(response.body()!!)
        } else
            LoginState.Failure(errorMessage = "Wrong Credentials or try again")
    }

    // This calls the API and posts the new User Request to the Server
    suspend fun postSignupRequest(postSignupData: PostSignupData): RegistrationState {

        // Signup Request Response From the Server
        val response = apiInstance.postSignupRequest(postSignupData)

        return if (response.isSuccessful)
            RegistrationState.Success(response.body()!!)
        else
            RegistrationState.Failure(errorMessage = "Please Check the Data entered")
    }
}