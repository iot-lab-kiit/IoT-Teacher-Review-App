package `in`.iot.lab.design.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.animations.AmongUsAnimation
import `in`.iot.lab.design.animations.EmptyListAnimation
import `in`.iot.lab.design.animations.InternetErrorAnimation
import `in`.iot.lab.design.animations.ServerErrorAnimation
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNAL_SERVER_ERROR
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNET_ERROR


@Composable
fun <T : Any> LazyPagingItems<T>.HandlePagingData(
    loadingBlock: @Composable () -> Unit = { AmongUsAnimation() },
    onCancel: (() -> Unit)? = null,
    successBlock: @Composable (LazyPagingItems<T>) -> Unit
) {

    successBlock(this)

    when {

        loadState.refresh is LoadState.Error -> {

            // Error Code and message
            val code = (loadState.refresh as LoadState.Error).getCode()
            val errorMessage = (loadState.refresh as LoadState.Error).getMessage()


            when (code) {

                INTERNAL_SERVER_ERROR.toString() -> {
                    ServerErrorAnimation(
                        message = errorMessage,
                        onTryAgainClick = this::refresh
                    )
                }

                INTERNET_ERROR.toString() -> {
                    InternetErrorAnimation(onTryAgainClick = this::refresh)
                }

                else -> {
                    AppFailureScreen(
                        text = errorMessage,
                        onCancel = onCancel ?: {},
                        onTryAgain = this::refresh
                    )
                }
            }
        }

        itemCount == 0 && loadState.refresh !is LoadState.Loading -> {
            EmptyListAnimation(onTryAgainClick = this::refresh)
        }

        loadState.append is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) { loadingBlock() }
        }

        loadState.refresh is LoadState.Loading -> {
            loadingBlock()
        }
    }
}


/**
 * This function fetches the error code from the Throwable.
 */
fun LoadState.Error.getCode(): String {
    return try {
        error
            .message
            .toString()
            .substring(0, 3)
    } catch (e: Exception) {
        "No Code"
    }
}


/**
 * This function This function fetches the error message from the Throwable.
 */
fun LoadState.Error.getMessage(): String {
    return try {
        error
            .message
            .toString()
            .substring(6)
    } catch (e: Exception) {
        "Unknown Error"
    }
}