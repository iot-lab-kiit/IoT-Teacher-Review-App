package `in`.iot.lab.review.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import `in`.iot.lab.design.components.AppScaffold
import `in`.iot.lab.design.components.AppScreen
import `in`.iot.lab.design.components.FAB
import `in`.iot.lab.design.components.LocalHazeState
import `in`.iot.lab.design.components.ReviewDataUI
import `in`.iot.lab.design.state.HandlePagingData
import `in`.iot.lab.design.state.HandleUiState
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.kritique.domain.models.review.RemoteFacultyReview
import `in`.iot.lab.network.state.UiState
import `in`.iot.lab.review.view.components.FacultyReviewDataUI
import `in`.iot.lab.review.view.components.isScrollingUp
import `in`.iot.lab.review.view.events.FacultyEvent
import kotlinx.coroutines.launch

@Composable
fun ReviewDetailScreenControl(
    facultyData: UiState<RemoteFaculty>,
    reviewList: LazyPagingItems<RemoteFacultyReview>,
    onFabClick: () -> Unit,
    onBackClick: () -> Unit,
    setEvent: (FacultyEvent) -> Unit,
    bookmarkedIds: Set<String> = emptySet(),
    onBookmarkClick: (RemoteFaculty) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        setEvent(FacultyEvent.GetFacultyDetails)
    }

    val lazyListState     = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope             = rememberCoroutineScope()

    AppScreen {
        AppScaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                Box(modifier = Modifier.padding(bottom = 75.dp)) {
                    FAB(
                        text     = "Review",
                        onClick  = onFabClick,
                        extended = lazyListState.isScrollingUp()
                    )
                }
            },
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets
                .exclude(NavigationBarDefaults.windowInsets)
        ) {
            facultyData.HandleUiState(
                onCancel   = onBackClick,
                onTryAgain = { setEvent(FacultyEvent.GetFacultyDetails) }
            ) { faculty ->
                reviewList.HandlePagingData {
                    ReviewDetailSuccessScreen(
                        faculty         = faculty,
                        reviewList      = reviewList,
                        lazyListState   = lazyListState,
                        isBookmarked    = bookmarkedIds.contains(faculty.id),
                        onBookmarkClick = {
                            // ✅ Read BEFORE toggle so message is correct
                            val wasBookmarked = bookmarkedIds.contains(faculty.id)
                            onBookmarkClick(faculty)
                            val msg = if (wasBookmarked) "Bookmark removed" else "Teacher bookmarked! ✓"
                            scope.launch { snackbarHostState.showSnackbar(msg) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewDetailSuccessScreen(
    faculty: RemoteFaculty,
    reviewList: LazyPagingItems<RemoteFacultyReview>,
    lazyListState: LazyListState,
    isBookmarked: Boolean = false,
    onBookmarkClick: () -> Unit = {}
) {
    val hazeState = LocalHazeState.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        state             = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding    = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        item {
            hazeState?.let { state ->
                FacultyReviewDataUI(
                    name            = faculty.name,
                    photoUrl        = faculty.photoUrl ?: "",
                    experience      = faculty.experience,
                    avgRating       = faculty.avgRating ?: 0.0,
                    totalRating     = faculty.totalRating ?: 0,
                    isBookmarked    = isBookmarked,
                    onBookmarkClick = onBookmarkClick,
                    hazeState       = state
                )
            }
        }

        faculty.totalRating?.let {
            if (it != 0) {
                item {
                    Text(
                        text  = "Reviews ($it)",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        items(reviewList.itemCount) { index ->
            reviewList[index]?.let { review ->
                ReviewDataUI(
                    title            = review.createdBy?.anonymousName ?: "Reviewer Name",
                    rating           = review.rating ?: 0.0,
                    description      = review.feedback ?: "Alas! The reviewer gave no feedback",
                    photoUrl         = review.createdBy?.photoUrl ?: "",
                    showFacultyPhoto = false,
                    showMenu         = false,
                    createdAt        = review.createdAt ?: ""
                )
            }
        }
    }
}