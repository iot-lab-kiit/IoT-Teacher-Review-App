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
import `in`.iot.lab.network.utils.NetworkStatusCodes.SERVER_UNDER_MAINTENANCE_EC2
import `in`.iot.lab.network.utils.NetworkStatusCodes.SERVER_UNDER_MAINTENANCE_NGROK


@Composable
fun <T : Any> LazyPagingItems<T>.HandlePagingData(
    loadingBlock: @Composable () -> Unit = { AmongUsAnimation() },
    onCancel: (() -> Unit)? = null,
    successBlock: @Composable (LazyPagingItems<T>) -> Unit
) {

      when {

        loadState.refresh is LoadState.Error -> {

            val error = (loadState.refresh as LoadState.Error).error
            val message = error.message ?: ""

            // Safely extract error code as Int
            val code = try {
                message.trim().split(" ").firstOrNull()?.toIntOrNull()
                    ?: message.trim().toIntOrNull()
                    ?: -1
            } catch (e: Exception) {
                -1
            }

            // Safely extract error message (everything after code)
            val errorMessage = try {
                val spaceIndex = message.indexOf(" ")
                if (spaceIndex != -1) message.substring(spaceIndex + 1)
                else message
            } catch (e: Exception) {
                message
            }

            when (code) {

                INTERNET_ERROR -> {
                    InternetErrorAnimation(
                        onTryAgainClick = this::refresh
                    )
                }

                INTERNAL_SERVER_ERROR,
                SERVER_UNDER_MAINTENANCE_EC2,
                SERVER_UNDER_MAINTENANCE_NGROK -> {
                    ServerErrorAnimation(
                        message = errorMessage,
                        onTryAgainClick = this::refresh
                    )
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

        loadState.refresh is LoadState.Loading -> {
            loadingBlock()
        }

        itemCount == 0 -> {
            EmptyListAnimation(onTryAgainClick = this::refresh)
        }

        else -> {
            successBlock(this)
        }
    }
}