package `in`.iot.lab.design.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import `in`.iot.lab.design.theme.*
import java.text.SimpleDateFormat
import java.util.TimeZone


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
                    "industry. Lorem Ipsum has been the industry's standard dummy text.",
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
    showMenu: Boolean = true,
    onEditPress: (() -> Unit)? = null,
    onDeletePress: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    // Glass card container
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        GlassSurface.copy(alpha = 0.95f),
                        surfaceVariantColor.copy(alpha = 0.85f)
                    )
                )
            )
    ) {
        // Subtle left accent bar
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            primaryColor,
                            primaryColor.copy(alpha = 0.0f)
                        )
                    )
                )
        )

        // Subtle border
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
        ) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = GlassBorder.copy(alpha = 0.5f)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Avatar
                if (showFacultyPhoto && photoUrl.isNotBlank()) {
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(44.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    LetterAvatar(
                        name = title,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StarUI(rating = rating)

                        // Date
                        val formatReceived = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'")
                        formatReceived.timeZone = TimeZone.getTimeZone("GMT+5.30")
                        val date = formatReceived.parse(createdAt) ?: "No Date"
                        val desiredFormat = SimpleDateFormat("dd-MM-yyyy").format(date)

                        Text(
                            text = "· $desiredFormat",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Menu
                if (showMenu && (onEditPress != null || onDeletePress != null)) {
                    Box {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (expanded) {
                            Popup(
                                alignment = Alignment.TopEnd,
                                onDismissRequest = { expanded = false }
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = surfaceVariantColor,
                                    border = CardDefaults.outlinedCardBorder().copy(
                                        width = 1.dp
                                    ),
                                    tonalElevation = 8.dp,
                                    shadowElevation = 12.dp
                                ) {
                                    Column {
                                        onEditPress?.let {
                                            Row(
                                                modifier = Modifier
                                                    .clickable {
                                                        expanded = false
                                                        it()
                                                    }
                                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    Icons.Default.Edit,
                                                    null,
                                                    tint = primaryColor
                                                )
                                                Spacer(Modifier.width(12.dp))
                                                Text(
                                                    "Edit Review",
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }

                                        onDeletePress?.let {
                                            Row(
                                                modifier = Modifier
                                                    .clickable {
                                                        expanded = false
                                                        it()
                                                    }
                                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    Icons.Default.DeleteForever,
                                                    null,
                                                    tint = errorColor
                                                )
                                                Spacer(Modifier.width(12.dp))
                                                Text(
                                                    "Delete Review",
                                                    color = errorColor
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

            // Description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}