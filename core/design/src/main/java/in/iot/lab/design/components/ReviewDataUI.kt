package `in`.iot.lab.design.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import `in`.iot.lab.design.R
import `in`.iot.lab.design.theme.CustomAppTheme
import java.text.SimpleDateFormat
import java.util.TimeZone


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    CustomAppTheme {
        ReviewDataUI(
            title = "Anirban Basak",
            rating = 4.8,
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting " +
                    "industry. Lorem Ipsum has been the industry's standard dummy text ever " +
                    "since the 1500s, when an unknown printer took a galley of type and " +
                    "scrambled it to make a type specimen book.",
            photoUrl = "",
            createdAt = "2024-05-29T11:50:22.446Z",
            onEditPress = {},
            onDeletePress = {}
        )
    }
}


@SuppressLint("SimpleDateFormat")
@Composable
fun ReviewDataUI(
    modifier: Modifier = Modifier,
    title: String,
    rating: Double,
    description: String,
    photoUrl: String,
    createdAt: String,
    showFacultyPhoto: Boolean = false,
    showMenu: Boolean = true, //(true in history screen, false in review list)
    onEditPress: (() -> Unit)? = null,
    onDeletePress: (() -> Unit)? = null
) {

    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors()
    ) {

        var expanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Profile Pic Image
                if (showFacultyPhoto && photoUrl.isNotBlank()) {
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(48.dp),
                        contentScale = ContentScale.Crop
                    )

                } else {
                    LetterAvatar(
                        name = title,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {

                    // Name Text
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        // This function shows the Star UI
                        StarUI(rating = rating)

                        // Formatting the Date
                        val formatReceived = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'")
                        formatReceived.timeZone = TimeZone.getTimeZone("GMT+5.30")
                        val date = formatReceived.parse(createdAt) ?: "No Date"
                        val desiredFormat = SimpleDateFormat("dd-MM-yyyy").format(date)

                        // Creation Date Text
                        Text(
                            text = "·  $desiredFormat",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                if (showMenu && (onEditPress != null || onDeletePress != null)) {
                    Box {
                        IconButton(
                            onClick = { expanded = !expanded }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menu"
                            )
                        }
                        if (expanded) {
                            Popup(
                                alignment = Alignment.TopEnd,
                                onDismissRequest = { expanded = false }
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    tonalElevation = 6.dp,
                                    shadowElevation = 8.dp
                                ) {
                                    Column {

                                        onEditPress?.let {
                                            Row(
                                                modifier = Modifier
                                                    .clickable {
                                                        expanded = false
                                                        it()
                                                    }
                                                    .padding(14.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                Icon(
                                                    Icons.Default.Edit,
                                                    null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )

                                                Spacer(Modifier.width(12.dp))

                                                Text("Edit Review")
                                            }
                                        }

                                        onDeletePress?.let {
                                            Row(
                                                modifier = Modifier
                                                    .clickable {
                                                        expanded = false
                                                        it()
                                                    }
                                                    .padding(14.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                Icon(
                                                    Icons.Default.DeleteForever,
                                                    null,
                                                    tint = MaterialTheme.colorScheme.error
                                                )

                                                Spacer(Modifier.width(12.dp))

                                                Text(
                                                    "Delete Review",
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Description Text
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}