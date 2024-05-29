package `in`.iot.lab.review.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.smarttoolfactory.ratingbar.model.RatingInterval
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.theme.CustomAppTheme


// Preview Function
@Preview("Light Rating")
@Preview(
    name = "Dark Rating",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    CustomAppTheme {
        AppScreen {
            Column {
                AppRatingBar(
                    rating = 5f,
                    onRatingChange = {}
                )
            }
        }
    }
}


/** [AppRatingBar] that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 * Available ImgSources = ImageBitmap/ImageVector/Painter
 * @param rating value to be set on this rating bar
 * @param imageVectorEmpty background for rating items. Item with borders to
 * show empty values
 * @param imageVectorFilled foreground for rating items. Filled item to show percentage of rating
 * @param tintEmpty color for background and foreground items
 * @param itemSize size of the rating item to be displayed. This is intrinsic size of image
 * or vector file by default
 * @param itemCount maximum number of items
 * @param space space between rating items in dp
 * [RatingInterval.Half] returns multiples of 0.5, and [RatingInterval.Unconstrained] returns
 * current value without any limitation up to [itemSize]
 * @param allowZeroRating when true [RatingInterval.Full] or [RatingInterval.Half] allows
 * user to set value zero
 * @param onRatingChange callback to notify user when rating has changed. This is helpful
 * for getting change after tap or drag gesture
 *
 */
@Composable
fun AppRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    imageVectorEmpty: ImageVector = Icons.Outlined.StarBorder,
    imageVectorFilled: ImageVector = Icons.Filled.Star,
    tintEmpty: Color = MaterialTheme.colorScheme.primary,
    tintFilled: Color = MaterialTheme.colorScheme.primary,
    itemSize: Dp = 48.dp,
    itemCount: Int = 5,
    space: Dp = 8.dp,
    allowZeroRating: Boolean = false,
    onRatingChange: (Float) -> Unit
) {
    RatingBar(
        rating = rating,
        modifier = modifier,
        imageVectorEmpty = imageVectorEmpty,
        imageVectorFilled = imageVectorFilled,
        onRatingChange = onRatingChange,
        tintEmpty = tintEmpty,
        tintFilled = tintFilled,
        itemCount = itemCount,
        itemSize = itemSize,
        gestureStrategy = GestureStrategy.DragAndPress,
        rateChangeStrategy = RateChangeStrategy.AnimatedChange(),
        shimmerEffect = null,
        space = space,
        ratingInterval = RatingInterval.Full,
        allowZeroRating = allowZeroRating
    )
}