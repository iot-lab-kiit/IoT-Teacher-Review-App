package `in`.iot.lab.kritique.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.chrisbanes.haze.HazeState
import `in`.iot.lab.design.components.LocalHazeState
import `in`.iot.lab.kritique.view.vm.BookmarkViewModel
import `in`.iot.lab.review.view.components.FacultyDataUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    onExploreClick: () -> Unit,
    onFacultyClick: (String) -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val faculties by viewModel.bookmarkedFaculties.collectAsState()
    val hazeState = LocalHazeState.current ?: remember { HazeState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookmarks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        if (faculties.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                EmptyBookmarksState(onExploreClick = onExploreClick)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(faculties, key = { it.id ?: it.hashCode() }) { faculty ->
                    FacultyDataUI(
                        modifier = Modifier.clickable {
                            // ✅ Pass faculty ID up — HomeNavGraph will set it on
                            // FacultyViewModel before navigating to detail
                            faculty.id?.let { onFacultyClick(it) }
                        },
                        name = faculty.name ?: "",
                        photoUrl = faculty.photoUrl ?: "",
                        experience = faculty.experience,
                        avgRating = faculty.avgRating ?: 0.0,
                        totalRating = faculty.totalRating ?: 0,
                        hazeState = hazeState,
                        onRemoveBookmark = {
                            faculty.id?.let { viewModel.removeBookmark(it) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyBookmarksState(onExploreClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(Icons.Outlined.BookmarkBorder, null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        Text("No Bookmarks Yet",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text("Save your favorite faculties here for quick access later",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onExploreClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(Icons.Filled.Explore, null, Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Explore Faculties")
        }
    }
}