package `in`.iot.lab.kritique.view.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.kritique.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: BookmarkRepository
) : ViewModel() {

    /** IDs of bookmarked faculties — used to show the filled/outlined icon. */
    val bookmarkedIds: StateFlow<Set<String>> = repository.bookmarkedIds
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    /** Full faculty objects shown on the Bookmark screen. */
    val bookmarkedFaculties: StateFlow<List<RemoteFaculty>> = repository.bookmarkedFaculties
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /** Toggle from the teacher review page: bookmark if not saved, else un-bookmark. */
    fun toggleBookmark(faculty: RemoteFaculty) = repository.toggle(faculty)

    /** Remove from the Bookmark screen. */
    fun removeBookmark(facultyId: String) = repository.remove(facultyId)
}
