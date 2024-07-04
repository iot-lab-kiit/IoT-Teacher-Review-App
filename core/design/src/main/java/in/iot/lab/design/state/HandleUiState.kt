package `in`.iot.lab.design.state

import androidx.compose.runtime.Composable
import `in`.iot.lab.design.animations.AmongUsAnimation
import `in`.iot.lab.design.animations.EmptyListAnimation
import `in`.iot.lab.design.animations.InternetErrorAnimation
import `in`.iot.lab.design.animations.ServerErrorAnimation
import `in`.iot.lab.design.components.AppFailureScreen
import `in`.iot.lab.network.state.UiState


@Composable
fun <T> UiState<T>.HandleUiState(
    idleBlock: @Composable (() -> Unit)? = null,
    loadingBlock: @Composable () -> Unit = { AmongUsAnimation() },
    onCancel: () -> Unit,
    onTryAgain: () -> Unit,
    successBlock: @Composable ((T) -> Unit)? = null
) {
    when (this) {
        is UiState.Idle -> {
            idleBlock?.let { it() }
        }

        is UiState.Loading -> {
            loadingBlock()
        }

        is UiState.Success -> {
            successBlock?.let { it(data) }
        }

        is UiState.Failed -> {
            AppFailureScreen(
                text = message,
                onCancel = onCancel,
                onTryAgain = onTryAgain
            )
        }

        is UiState.NoDataFound -> {
            EmptyListAnimation(onTryAgainClick = onTryAgain)
        }

        is UiState.InternetError -> {
            InternetErrorAnimation(onTryAgainClick = onTryAgain)
        }

        is UiState.InternalServerError -> {
            ServerErrorAnimation(
                message = errorMessage,
                onTryAgainClick = onTryAgain
            )
        }
    }
}