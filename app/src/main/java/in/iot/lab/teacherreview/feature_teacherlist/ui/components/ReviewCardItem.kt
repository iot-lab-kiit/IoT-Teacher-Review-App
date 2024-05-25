package `in`.iot.lab.teacherreview.feature_teacherlist.ui.components

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.design.theme.redDot
import `in`.iot.lab.design.theme.yellowDot
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.User
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

// This is the Preview function of the Teacher Review Control Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewControl() {
    CustomAppTheme {
        ReviewCardItem(
            createdBy = User(
                name = "Anirban Basak",
            ),
            review = "If you know how to deal with few " +
                    "seniors who have ego issues, you will " +
                    "ace at your work. Just learn how to take" +
                    " command over few egoistic coordinators " +
                    "or HODs, DPS will be the best place for " +
                    "work and to build your career.",
            rating = 4.3,
            createdAt = "2021-09-01T10:15:30.000Z",
        )
    }
}

/**
 * This is the UI for Each Card Item of the Review Section
 *
 * @param modifier Default so that the parent can pass modifications to the Child
 * @param createdBy This is the User who created the Review
 * @param review This is the Review
 */
@Composable
fun ReviewCardItem(
    modifier: Modifier = Modifier,
    createdBy: User,
    review: String,
    rating: Double = 0.0,
    createdAt: String,
    currentUserId: String? = "",
    onDelete: () -> Unit = {}
) {
    val context = LocalContext.current
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Profile Photo of the Reviewer
                AsyncImage(
                    model = createdBy.photoUrl,
                    placeholder = painterResource(id = R.drawable.profile_photo),
                    contentDescription = stringResource(id = R.string.profile),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.inversePrimary, CircleShape)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = createdBy.name ?: "Anonymous",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.secondary,
                        )
                        if (!currentUserId.isNullOrEmpty()) {
                            if (currentUserId == createdBy.id) {
                                ReviewOptionsDropdown(
                                    onDelete = onDelete
                                )
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = rating.toString().substring(0, 2),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Stars",
                            modifier = Modifier.size(16.dp),
                            tint = when {
                                rating < 2.5 -> redDot
                                rating < 3.5 -> yellowDot
                                else -> Color.Green
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        // Dot Separator
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = formatCreatedAt(createdAt),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                }
            }

            Text(
                text = review,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionButton(
                    title = "Helpful",
                    onClick = {
                        Log.d("ReviewCardItem", "Like Clicked")
                        Toast.makeText(
                            context,
                            "TODO: Implement Like Functionality",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Helpful",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )
                ActionButton(
                    title = "Report",
                    onClick = {
                        Log.d("ReviewCardItem", "Report Clicked")
                        Toast.makeText(
                            context,
                            "TODO: Implement Report Functionality",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ReportProblem,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ActionButton(
    title: String = "Like",
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        icon()
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// TODO: Use Date Formatter library to format the Date coming from the API correctly
private fun formatCreatedAt(timestamp: String): String {
    try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val parsedTime = LocalDateTime.parse(timestamp, formatter)
        val currentTime = LocalDateTime.now(ZoneId.of("UTC"))

        val duration = Duration.between(parsedTime, currentTime)
        val seconds = duration.seconds

        return when {
            seconds < 60 -> "Just now"
            seconds < 3600 -> "${seconds / 60} minutes ago"
            seconds < 86400 -> "${seconds / 3600} hours ago"
            else -> "${seconds / 86400} days ago"
        }
    } catch (e: Exception) {
        return "Unknown"
    }

}

@Composable
fun ReviewOptionsDropdown(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Options",
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    expanded = !expanded
                }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = {
                Text(
                    text = "Delete",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }, onClick = onDelete)
        }
    }
}