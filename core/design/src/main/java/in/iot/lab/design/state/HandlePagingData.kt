package `in`.iot.lab.design.state

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.animations.AmongUsAnimation
import `in`.iot.lab.design.animations.EmptyListAnimation
import `in`.iot.lab.design.animations.InternetErrorAnimation
import `in`.iot.lab.design.animations.ServerErrorAnimation
import `in`.iot.lab.design.components.AppFailureScreen


@Composable
fun <T : Any> LazyPagingItems<T>.HandlePagingData(
    loadingBlock: @Composable () -> Unit = { AmongUsAnimation() },
    onCancel: (() -> Unit)? = null,
    successBlock: @Composable (LazyPagingItems<T>) -> Unit
) {

    successBlock(this)

    when {

        loadState.refresh is LoadState.Loading -> {
            loadingBlock()
        }

        loadState.append is LoadState.Loading -> {
            loadingBlock()
        }

        loadState.refresh is LoadState.Error -> {

            val errorMessage = (loadState.refresh as LoadState.Error).error.message.toString()

            when {
                errorMessage.contains("204")
                        || errorMessage.contains("205")
                        || errorMessage.contains("206") -> {
                    EmptyListAnimation()
                }

                errorMessage.contains("405") -> {
                    InternetErrorAnimation()
                }

                errorMessage.contains("404") -> {
                    ServerErrorAnimation()
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