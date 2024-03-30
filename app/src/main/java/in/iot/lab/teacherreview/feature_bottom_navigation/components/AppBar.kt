package `in`.iot.lab.teacherreview.feature_bottom_navigation.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.design.theme.*

// This is the Preview function of the Top App Bar
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        AppBar(
            topBarTitle = R.string.app_name,
            icon = Icons.Default.ArrowBack
        )
    }
}

/**
 * This is the Composable for the App Top Bar
 *
 * @param modifier Default Modifier so that the Parent Function can send something if needed
 * @param topBarTitle The Title of the Top Bar will be passes by this
 * @param icon The Image which needs to be shown at the Left most Side will be passed here
 * @param contentDescription The Content Description of the Icon will be passed here
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    @StringRes topBarTitle: Int,
    icon: ImageVector? = null,
    @StringRes contentDescription: Int? = null
) {

    // This Top App Bar aligns the title inside Centred Horizontally
    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription?.let { stringResource(id = it) }
                )
            }
        },
        title = {
            Text(text = stringResource(id = topBarTitle))
        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer
//        )
    )
}