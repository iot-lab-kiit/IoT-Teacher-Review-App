package `in`.iot.lab.teacherreview.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp


/*
You can customize the shape system for all components in the MaterialTheme
or you can do it on a per component basis.
*/

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
    large = RoundedCornerShape(0.dp)
)

val buttonShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp , bottomStart = 16.dp , topEnd = 16.dp)