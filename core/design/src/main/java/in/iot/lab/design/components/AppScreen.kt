package `in`.iot.lab.design.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import `in`.iot.lab.design.theme.CustomAppTheme


/**
 * This function is used to Give the Default Background Colors and Alignment
 *
 * @param modifier (Optional) Modifier can be passed from the parent function to this
 * @param body This is the Composable that should be inside this
 */
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    body: @Composable BoxScope.() -> Unit
) {

    CustomAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            snackbarHost = snackbarHost
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = contentAlignment,
                content = body
            )
        }
    }
}