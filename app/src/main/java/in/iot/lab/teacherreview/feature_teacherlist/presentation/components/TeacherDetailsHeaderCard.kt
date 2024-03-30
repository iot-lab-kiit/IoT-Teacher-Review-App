package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData

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
    selectedTeacher: IndividualFacultyData
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Teacher Profile Picture
        AsyncImage(
            model = selectedTeacher.avatar,
            placeholder = painterResource(id = R.drawable.profile_photo),
            contentDescription = stringResource(id = R.string.profile),
            modifier = Modifier
                .size(72.dp)
                .clip(shape = RoundedCornerShape(10.dp))
        )

        // Space of Height 8 dp
        Spacer(modifier = Modifier.height(8.dp))

        // Teacher Name
        Text(
            text = selectedTeacher.name,
            style = MaterialTheme.typography.headlineSmall,
        )

        // Average Rating of the Teacher
        StarsRow(starCount = selectedTeacher.avgRating)

        // Space of Height 24 dp
        Spacer(modifier = Modifier.height(24.dp))

        // Binding the rating and the strings together
        val ratingParams = listOf(
            Pair(
                stringResource(R.string.teaching_rating),
                selectedTeacher.avgTeachingRating
            ),
            Pair(
                stringResource(R.string.marking_rating),
                selectedTeacher.avgMarkingRating
            ),
            Pair(
                stringResource(R.string.attendance_rating),
                selectedTeacher.avgAttendanceRating
            )
        )

        // Drawing all the 3 Cards for the teacher Rating Details
        for (rating in ratingParams) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {

                // This Row contains the Text and the average stars gotten by the teacher
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Category of Rating
                    Text(
                        text = rating.first,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(start = 24.dp)
                    )

                    // Average Stars in that category
                    StarsRow(
                        starCount = rating.second,
                        modifier = Modifier
                            .size(18.dp)
                    )
                }
            }

            // Space of height 16 dp between the Cards
            Spacer(Modifier.height(16.dp))
        }

        // Reviews Text
        Text(
            text = stringResource(R.string.reviews),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}