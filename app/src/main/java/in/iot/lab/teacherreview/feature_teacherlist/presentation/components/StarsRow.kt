package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import kotlin.math.roundToInt

// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        StarsRow(starCount = 5.0)
    }
}

/**
 * The Main Register Screen of this File which calls all the Other Composable functions and places them
 *
 * @param modifier  Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 * @param starCount This is the count of Stars that needs to be shown
 */
@Composable
fun StarsRow(
    modifier: Modifier = Modifier,
    starCount: Double
) {

    // This variable stores the Rounded up value for number of stars to be shown
    val stars = starCount.roundToInt()

    // This Function draws the Stars accordingly
    LazyRow(
        modifier = modifier
            .padding(2.dp)
    ) {
        items(stars) {
            Image(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.star),
                modifier = Modifier
                    .size(12.dp)
            )
        }
    }
}