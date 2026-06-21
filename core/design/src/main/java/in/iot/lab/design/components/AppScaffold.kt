package `in`.iot.lab.design.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import `in`.iot.lab.design.theme.CustomAppTheme

// AppScaffold is intentionally simple — it does NOT own a HazeState.
// HazeState lives in AppScreen (via LocalHazeState) and flows down
// to any card that needs it via LocalHazeState.current
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    body: @Composable BoxScope.() -> Unit
) {
    CustomAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            snackbarHost = snackbarHost,
            contentWindowInsets = contentWindowInsets,
            containerColor = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(top = it.calculateTopPadding()),
                contentAlignment = contentAlignment,
                content = body
            )
        }
    }
}