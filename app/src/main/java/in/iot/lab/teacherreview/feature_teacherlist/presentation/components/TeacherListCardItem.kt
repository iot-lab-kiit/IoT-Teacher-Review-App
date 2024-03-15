package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.core.theme.muted
import `in`.iot.lab.teacherreview.core.theme.redDot
import `in`.iot.lab.teacherreview.core.theme.yellowDot
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData

// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        TeacherListCardItem(
            navController = rememberNavController(),
            teacher = IndividualFacultyData(
                _id = "82323dhub21dh181dbd1x",
                name = "Anirban Basak",
                code = "Operating System",
                avatar = "",
                avgAttendanceRating = 3.1,
                avgMarkingRating = 3.9,
                avgTeachingRating = 4.0
            ),
        ) {}
    }
}

/**
 * The Main Register Screen of this File which calls all the Other Composable functions and places them
 *
 * @param navController This is the NavController Object which is used to navigate Screens
 * @param modifier  Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 */
@Composable
fun TeacherListCardItem(
    modifier: Modifier = Modifier,
    navController: NavController,
    teacher: IndividualFacultyData,
    onTeacherClick: () -> Unit
) {

    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onTeacherClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp,
            hoveredElevation = 12.dp,
            disabledElevation = 0.dp,
            draggedElevation = 12.dp,
            focusedElevation = 12.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = teacher.avatar,
                    placeholder = painterResource(id = R.drawable.profile_photo),
                    contentDescription = stringResource(id = R.string.profile),
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = teacher.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = teacher.code ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = muted
                        )
                    }
                    RatingDot(rating = teacher.avgRating)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.height(1.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val color = when {
                    teacher.avgRating < 2.5 -> redDot
                    teacher.avgRating < 3.5 -> yellowDot
                    else -> Color.Green
                }
                // Rating only 1 decimal places shown
                val rating = teacher.avgRating.toString().substring(0, 3)
                Text(
                    text = "$rating Rating",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color
                )
                Spacer(modifier = Modifier.width(80.dp))
                Text(
                    text = "${teacher.totalReviews} Reviews",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun RatingDot(rating: Double) {
    val color = when {
        rating < 2.5 -> redDot
        rating < 3.5 -> yellowDot
        else -> Color.Green
    }
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(color)
    )
}
