package `in`.iot.lab.network.utils

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import `in`.iot.lab.network.data.CustomResponse
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkStatusCodes.CREATED
import `in`.iot.lab.network.utils.NetworkStatusCodes.DELETED
import `in`.iot.lab.network.utils.NetworkStatusCodes.FACULTY_NOT_FOUND
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNAL_SERVER_ERROR
import `in`.iot.lab.network.utils.NetworkStatusCodes.INVALID_REQUEST
import `in`.iot.lab.network.utils.NetworkStatusCodes.INVALID_TOKEN
import `in`.iot.lab.network.utils.NetworkStatusCodes.REVIEW_NOT_FOUND
import `in`.iot.lab.network.utils.NetworkStatusCodes.SUCCESSFUL
import `in`.iot.lab.network.utils.NetworkStatusCodes.TOKEN_REQUIRED
import `in`.iot.lab.network.utils.NetworkStatusCodes.UNAUTHORIZED
import `in`.iot.lab.network.utils.NetworkStatusCodes.UPDATED
import `in`.iot.lab.network.utils.NetworkStatusCodes.USER_NOT_FOUND
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.concurrent.TimeoutException

object NetworkUtil {


    /**
     * This function is a wrapper function over the Retrofit Api calls to make the exception
     * handling easier and less boilerplate code needs to be generated
     */
    suspend fun <T> getFlowState(
        onSuccess: suspend (ResponseState.Success<T>) -> Unit = {},
        onFailure: suspend () -> Unit = {},
        request: suspend () -> CustomResponse<T>
    ): Flow<ResponseState<T>> {
        return flow {

            emit(ResponseState.Loading)
            try {

                // Response from the Retrofit Api call
                val response = request()
                val state = response.checkApiResponseStatusCode()

                when (state) {
                    is ResponseState.Success -> onSuccess(state)
                    is ResponseState.Loading -> Unit
                    else -> onFailure()
                }

                emit(state)
            } catch (e: TimeoutException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (exception: IOException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (e: FirebaseAuthInvalidCredentialsException) {

                onFailure()
                emit(ResponseState.InvalidToken)
            } catch (e: FirebaseException) {

                onFailure()
                emit(ResponseState.InvalidRequest)
            } catch (e: Exception) {

                // Calling the Custom Failure Function
                onFailure()
                emit(ResponseState.Error(e))
            }
        }
    }


    /**
     * This function is a wrapper function over the Retrofit Api calls to make the exception
     * handling easier and less boilerplate code needs to be generated
     */
    suspend fun getUnitFlowState(
        onFailure: suspend () -> Unit = {},
        request: suspend () -> Unit
    ): Flow<ResponseState<Unit>> {
        return flow {

            emit(ResponseState.Loading)
            try {
                request()
                emit(ResponseState.Success(Unit))
            } catch (e: TimeoutException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (exception: IOException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (e: FirebaseAuthInvalidCredentialsException) {

                onFailure()
                emit(ResponseState.InvalidToken)
            } catch (e: FirebaseException) {

                onFailure()
                emit(ResponseState.InvalidRequest)
            } catch (e: Exception) {

                // Calling the Custom Failure Function
                onFailure()
                emit(ResponseState.Error(e))
            }
        }
    }


    fun <T> CustomResponse<T>.checkApiResponseStatusCode(): ResponseState<T> {
        return when (status) {
            SUCCESSFUL, CREATED, DELETED, UPDATED -> ResponseState.Success(data = data!!)
            USER_NOT_FOUND, REVIEW_NOT_FOUND, FACULTY_NOT_FOUND -> ResponseState.NoDataFound
            INVALID_REQUEST -> ResponseState.InvalidRequest
            TOKEN_REQUIRED -> ResponseState.TokenRequired
            INVALID_TOKEN -> ResponseState.InvalidToken
            UNAUTHORIZED -> ResponseState.UnAuthorized
            INTERNAL_SERVER_ERROR -> ResponseState.ServerError
            else -> ResponseState.UnKnownError
        }
    }


    /**
     * This function is converting the [ResponseState] objects into [UiState] objects for later used
     * and passed down to the View Model Layer
     */
    fun <T> ResponseState<T>.toUiState(): UiState<T> {
        return when (this) {
            is ResponseState.NoInternet -> {
                UiState.InternetError
            }

            is ResponseState.NoDataFound -> {
                UiState.NoDataFound
            }

            is ResponseState.ServerError -> {
                UiState.ServerError
            }

            is ResponseState.Loading -> UiState.Loading

            is ResponseState.InvalidRequest -> {
                UiState.Failed("The api request is invalid and cannot be processed.")
            }

            is ResponseState.TokenRequired -> {
                UiState.Failed("Token missing!! Restart the app")
            }

            is ResponseState.InvalidToken -> {
                UiState.Failed("Token invalid!! Restart the app or clear data.")
            }

            is ResponseState.UnAuthorized -> {
                UiState.Failed("User not authorized")
            }

            is ResponseState.UnKnownError -> {
                UiState.Failed("Unknown error occurred!")
            }

            is ResponseState.Success -> {
                UiState.Success(this.data)
            }

            is ResponseState.Error -> {
                UiState.Failed(this.exception.message.toString())
            }
        }
    }
}