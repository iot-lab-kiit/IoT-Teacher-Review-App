package `in`.iot.lab.design.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import `in`.iot.lab.design.theme.*
import java.text.SimpleDateFormat
import java.util.TimeZone

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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        GlassSurface.copy(alpha = 0.97f),
                        surfaceVariantColor.copy(alpha = 0.88f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        GlassBorder.copy(alpha = 0.6f),
                        secondaryColor.copy(alpha = 0.10f)
                    )
                ),
                shape = RoundedCornerShape(18.dp)
            )
    ) {
        // Frosted shimmer
        Box(modifier = Modifier.matchParentSize().background(GlassOverlay))

        // Left accent bar — blue → purple
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(
                    Brush.verticalGradient(listOf(primaryColor, secondaryColor.copy(alpha = 0f)))
                )
        )

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
                if (showFacultyPhoto && photoUrl.isNotBlank()) {
                    AppNetworkImage(
                        model = photoUrl,
                        contentDescription = null,
                        modifier = Modifier.clip(CircleShape).size(44.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    LetterAvatar(name = title, modifier = Modifier.size(44.dp))
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
                        val fmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'")
                        fmt.timeZone = TimeZone.getTimeZone("GMT+5.30")
                        val date = fmt.parse(createdAt) ?: "No Date"
                        Text(
                            text = "· ${SimpleDateFormat("dd-MM-yyyy").format(date)}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (showMenu && (onEditPress != null || onDeletePress != null)) {
                    Box {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.MoreVert, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (expanded) {
                            Popup(alignment = Alignment.TopEnd, onDismissRequest = { expanded = false }) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(surfaceVariantColor)
                                        .border(1.dp, GlassBorder.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                                ) {
                                    Column {
                                        onEditPress?.let {
                                            Row(
                                                modifier = Modifier
                                                    .clickable { expanded = false; it() }
                                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.Default.Edit, null, tint = primaryColor)
                                                Spacer(Modifier.width(12.dp))
                                                Text("Edit Review", color = MaterialTheme.colorScheme.onSurface)
                                            }
                                        }
                                        onDeletePress?.let {
                                            Row(
                                                modifier = Modifier
                                                    .clickable { expanded = false; it() }
                                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.Default.DeleteForever, null, tint = errorColor)
                                                Spacer(Modifier.width(12.dp))
                                                Text("Delete Review", color = errorColor)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}