package `in`.iot.lab.design.state

import androidx.compose.runtime.Composable
import `in`.iot.lab.design.animations.AmongUsAnimation
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
                text = this.message,
                onCancel = onCancel,
                onTryAgain = onTryAgain
            )
        }
    }
}