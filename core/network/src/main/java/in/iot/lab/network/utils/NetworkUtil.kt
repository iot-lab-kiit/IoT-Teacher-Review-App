package `in`.iot.lab.network.utils

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

object NetworkUtil {


    /**
     * This function is a wrapper function over the Retrofit Api calls to make the exception
     * handling easier and less boilerplate code needs to be generated
     */
    suspend fun <T> getResponseState(
        onSuccess: suspend () -> Unit = {},
        onFailure: suspend (Exception) -> Unit = {},
        request: suspend () -> CustomResponse<T>
    ): Flow<ResponseState<T>> {

        return flow {
            emit(ResponseState.Loading)
            try {

                // Response from the Retrofit Api call
                val response = request()
                onSuccess()

                emit(response.checkApiResponseStatusCode())
            } catch (exception: IOException) {
                emit(ResponseState.NoInternet)
            } catch (e: Exception) {

                // Calling the Custom Failure Function
                onFailure(e)
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
                UiState.Failed("Oh no! Internet error! Try again~")
            }

            is ResponseState.NoDataFound -> {
                UiState.Failed("There are no records in the database! Try again later")
            }

            is ResponseState.ServerError -> {
                UiState.Failed("Oh shoot! Servers are down! Try again in a bit!")
            }

            is ResponseState.Loading -> UiState.Loading

            is ResponseState.Success -> {
                UiState.Success(this.data)
            }

            is ResponseState.Error -> {
                UiState.Failed(this.exception.message.toString())
            }

            // TODO: Handle other cases
            else -> {
                UiState.Failed("")
            }
        }
    }
}