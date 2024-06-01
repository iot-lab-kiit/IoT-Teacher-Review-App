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
import `in`.iot.lab.network.utils.NetworkStatusCodes.FACULTY_NOT_FOUND
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNAL_SERVER_ERROR
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNET_ERROR
import `in`.iot.lab.network.utils.NetworkStatusCodes.REVIEW_NOT_FOUND
import `in`.iot.lab.network.utils.NetworkStatusCodes.USER_NOT_FOUND


@Composable
fun <T : Any> LazyPagingItems<T>.HandlePagingData(
    loadingBlock: @Composable () -> Unit = { AmongUsAnimation() },
    onCancel: (() -> Unit)? = null,
    successBlock: @Composable (LazyPagingItems<T>) -> Unit
) {

    successBlock(this)

    when {

        loadState.append is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) { loadingBlock() }
        }

        loadState.refresh is LoadState.Loading -> {
            loadingBlock()
        }


        loadState.refresh is LoadState.Error -> {

            val errorMessage = (loadState.refresh as LoadState.Error).error.message.toString()

            when {
                errorMessage.contains(USER_NOT_FOUND.toString())
                        || errorMessage.contains(REVIEW_NOT_FOUND.toString())
                        || errorMessage.contains(FACULTY_NOT_FOUND.toString()) -> {
                    EmptyListAnimation(onTryAgainClick = this::refresh)
                }

                errorMessage.contains(INTERNAL_SERVER_ERROR.toString()) -> {
                    ServerErrorAnimation(onTryAgainClick = this::refresh)
                }

                errorMessage.contains(INTERNET_ERROR.toString()) -> {
                    InternetErrorAnimation(onTryAgainClick = this::refresh)
                }

                else -> {
                    AppFailureScreen(
                        text = (loadState.refresh as LoadState.Error).error.message.toString(),
                        onCancel = onCancel ?: {},
                        onTryAgain = this::refresh
                    )
                }
            }
        }
    }
}