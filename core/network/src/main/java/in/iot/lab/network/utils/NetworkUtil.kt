package `in`.iot.lab.network.utils

import com.google.firebase.FirebaseNetworkException
import `in`.iot.lab.network.data.CustomResponse
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.network.utils.NetworkStatusCodes.CREATED
import `in`.iot.lab.network.utils.NetworkStatusCodes.DELETED
import `in`.iot.lab.network.utils.NetworkStatusCodes.FACULTY_NOT_FOUND
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNAL_SERVER_ERROR
import `in`.iot.lab.network.utils.NetworkStatusCodes.REVIEW_NOT_FOUND
import `in`.iot.lab.network.utils.NetworkStatusCodes.SUCCESSFUL
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
            } catch (e : FirebaseNetworkException){
                onFailure()
                emit(ResponseState.NoInternet)
            } catch (e: TimeoutException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (exception: IOException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (e: Exception) {

                // Calling the Custom Failure Function
                onFailure()
                emit(ResponseState.Failed(e.message.toString()))
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
            } catch (e : FirebaseNetworkException){
                onFailure()
                emit(ResponseState.NoInternet)
            } catch (e: TimeoutException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (exception: IOException) {

                onFailure()
                emit(ResponseState.NoInternet)
            } catch (e: Exception) {

                // Calling the Custom Failure Function
                onFailure()
                emit(ResponseState.Failed(e.message.toString()))
            }
        }
    }


    fun <T> CustomResponse<T>.checkApiResponseStatusCode(): ResponseState<T> {
        return when (status) {
            SUCCESSFUL, CREATED, DELETED, UPDATED -> ResponseState.Success(data = data!!)
            USER_NOT_FOUND, REVIEW_NOT_FOUND, FACULTY_NOT_FOUND -> ResponseState.NoDataFound
            INTERNAL_SERVER_ERROR -> ResponseState.InternalServerError
            else -> ResponseState.Failed(
                errorMessage = message ?: ("Unknown Error Occurred!!" +
                        " Server haven't sent any error logs and messages")
            )
        }
    }


    /**
     * This function is converting the [ResponseState] objects into [UiState] objects for later used
     * and passed down to the View Model Layer
     */
    fun <T> ResponseState<T>.toUiState(): UiState<T> {
        return when (this) {

            // Loading State
            is ResponseState.Loading -> UiState.Loading

            // Success State
            is ResponseState.Success -> {
                UiState.Success(data)
            }

            // Error State
            is ResponseState.Failed -> {
                UiState.Failed(errorMessage)
            }

            // No Data from DB state
            is ResponseState.NoDataFound -> {
                UiState.NoDataFound
            }

            // Internal Server Error State
            is ResponseState.InternalServerError -> {
                UiState.InternalServerError
            }

            // No Internet State
            is ResponseState.NoInternet -> {
                UiState.InternetError
            }
        }
    }
}