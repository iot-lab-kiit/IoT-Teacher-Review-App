package `in`.iot.lab.teacherreview.feature_teacherlist.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.design.theme.redDot
import `in`.iot.lab.design.theme.yellowDot
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.IndividualFacultyData

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        TeacherDetailsHeaderCard(
            selectedTeacher = IndividualFacultyData(
                _id = "",
                name = "",
                avgTeachingRating = 3.2,
                avgMarkingRating = 4.3,
                avgAttendanceRating = 4.3,
                code = ""
            )
        )
    }
}

/**
 * This is the Main Screen for this File
 *
 * @param modifier Default to let the parent pass any Modifier modifications
 * @param selectedTeacher This is the Selected Teacher of the User
 */
@Composable
fun TeacherDetailsHeaderCard(
    modifier: Modifier = Modifier,
    selectedTeacher: IndividualFacultyData,
    onBackPressed: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go Back",
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.TopStart)
                    .padding(5.dp)
                    .clip(CircleShape)
                    .clickable { onBackPressed() }
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(
                            alpha = 0.4f
                        )
                    )
                    .padding(4.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            // Teacher Profile Picture
            AsyncImage(
                model = selectedTeacher.avatar,
                placeholder = painterResource(id = R.drawable.profile_photo),
                contentDescription = stringResource(id = R.string.profile),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 30.dp,
                            bottomEnd = 30.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedTeacher.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    modifier = Modifier
                        .weight(8f)
                )
                Badge(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .weight(2f),
                    containerColor = with(Unit) {
                        val color = when {
                            selectedTeacher.avgRating < 2.5 -> redDot
                            selectedTeacher.avgRating < 3.5 -> yellowDot
                            else -> Color.Green
                        }
                        color.copy(
                            alpha = 0.2f
                        )
                    },
                    contentColor = Color.White
                ) {
                    Text(
                        text = selectedTeacher.avgRating.toString().substring(0, 3),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                    )
                }
            }
        }


//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(220.dp)
//        ) {
//            IconButton(
//                modifier = Modifier.align(Alignment.TopStart).zIndex(1f),
//                onClick = onBackPressed
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBackIosNew,
//                    contentDescription = "Back",
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(180.dp)
//                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
//                    .background(MaterialTheme.colorScheme.primary)
//            )
//            TeacherListCardItem(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter),
//                teacher = selectedTeacher,
//                onTeacherClick = { }
//            )
//        }


//        // Space of Height 8 dp
//        Spacer(modifier = Modifier.height(8.dp))
//
//
//        // Average Rating of the Teacher
//
//
//        // Space of Height 24 dp
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Binding the rating and the strings together
//        val ratingParams = listOf(
//            Pair(
//                stringResource(R.string.teaching_rating),
//                selectedTeacher.avgTeachingRating
//            ),
//            Pair(
//                stringResource(R.string.marking_rating),
//                selectedTeacher.avgMarkingRating
//            ),
//            Pair(
//                stringResource(R.string.attendance_rating),
//                selectedTeacher.avgAttendanceRating
//            )
//        )
//
//        // Drawing all the 3 Cards for the teacher Rating Details
//        for (rating in ratingParams) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 16.dp),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//
//                // This Row contains the Text and the average stars gotten by the teacher
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    // Category of Rating
//                    Text(
//                        text = rating.first,
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier
//                            .padding(start = 24.dp)
//                    )
//
//                    // Average Stars in that category
//                    StarsRow(
//                        starCount = rating.second,
//                        modifier = Modifier
//                            .size(18.dp)
//                    )
//                }
//            }
//
//            // Space of height 16 dp between the Cards
//            Spacer(Modifier.height(16.dp))
//        }

        // Reviews Text
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.reviews),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}