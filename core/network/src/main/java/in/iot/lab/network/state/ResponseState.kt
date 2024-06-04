package `in`.iot.lab.network.state

import java.lang.Exception


/**
 * This class is used to defines the various types of states in an API call and each state is
 * defines by their respective data object classes
 *
 * @property NoInternet This means that the network is not working properly
 * @property NoDataFound This means that the database is empty and no such data found
 * @property ServerError This means that there is a problem in the server
 * @property Loading This means that the request is being processed currently.
 * @property Success This means that an api call is a success and we have received the required data
 * @property Error This is used to indicate other random Errors
 */
sealed interface ResponseState<out T> {
    data object NoInternet : ResponseState<Nothing>
    data object NoDataFound : ResponseState<Nothing>
    data object ServerError : ResponseState<Nothing>
    data object Loading : ResponseState<Nothing>
    data object ReviewAlreadyPosted : ResponseState<Nothing>
    data object InvalidRequest : ResponseState<Nothing>
    data object TokenRequired : ResponseState<Nothing>
    data object InvalidToken : ResponseState<Nothing>
    data object UnAuthorized : ResponseState<Nothing>
    data object UnKnownError : ResponseState<Nothing>
    data class Success<T>(val data: T) : ResponseState<T>
    data class Error(val exception: Exception) : ResponseState<Nothing>
    data object InvalidEmail : ResponseState<Nothing>
}