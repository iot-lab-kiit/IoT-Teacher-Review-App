package `in`.iot.lab.network.state


/**
 * This class is used to defines the various types of states in an API call and each state is
 * defines by their respective data object classes
 *
 * @property Idle This means that the api is currently not being used
 * @property Loading This means that an api call is already being sent
 * @property Success This means that an api call is a success
 * @property Failed This means that the api call is a failure and we didn't received data
 * @property NoDataFound This means that there is no data found in the Database.
 * @property InternetError This means that the network is not working properly
 * @property InternalServerError This means that there is an internal problem in the server.
 */
sealed interface UiState<out T> {

    // Basic General States
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Failed(val message: String) : UiState<Nothing>

    // Server Use Case States
    data object NoDataFound : UiState<Nothing>
    data object InternalServerError : UiState<Nothing>

    // Custom App Use Case States
    data object InternetError : UiState<Nothing>
}