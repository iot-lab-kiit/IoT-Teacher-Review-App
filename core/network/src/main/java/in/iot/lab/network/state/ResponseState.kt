package `in`.iot.lab.network.state


/**
 * This class is used to defines the various types of states in an API call and each state is
 * defines by their respective data object classes
 *
 * @property Loading This means that the request is being processed currently.
 * @property Success This means that an api call is a success and we have received the required data
 * @property Failed This is used to indicate other random Errors
 * @property NoDataFound This means that the database is empty and no such data found
 * @property InternalServerError This indicates that there is an internal error in the server
 * @property NoInternet This means that the network is not working properly
 */
sealed interface ResponseState<out T> {

    // Basic App Use Case States
    data object Loading : ResponseState<Nothing>
    data class Success<T>(val data: T) : ResponseState<T>
    data class Failed(val errorMessage: String) : ResponseState<Nothing>

    // Custom Server Required Use Case States
    data object NoDataFound : ResponseState<Nothing>
    data class InternalServerError(val errorMessage: String) : ResponseState<Nothing>

    // Custom App Required Use Case States
    data object NoInternet : ResponseState<Nothing>
}