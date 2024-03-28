package `in`.iot.lab.network.utils

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.state.UiState
import retrofit2.Response
import java.io.IOException

object NetworkUtil {


    /**
     * This function is a wrapper function over the Retrofit Api calls to make the exception
     * handling easier and less boilerplate code needs to be generated
     */
    suspend fun <T> getResponseState(
        onSuccess: suspend () -> Unit = {},
        onFailure: suspend (Exception) -> Unit = {},
        request: suspend () -> Response<T>
    ): ResponseState<T> {

        return try {

            // Response from the Retrofit Api call
            val response = request()

            if (response.isSuccessful) {
                onSuccess()
                ResponseState.Success(response.body()!!)
            } else
                ResponseState.NoDataFound

        } catch (exception: IOException) {
            ResponseState.NoInternet
        } catch (e: Exception) {

            // Calling the Custom Failure Function
            onFailure(e)
            ResponseState.Error(e)
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
        }
    }
}