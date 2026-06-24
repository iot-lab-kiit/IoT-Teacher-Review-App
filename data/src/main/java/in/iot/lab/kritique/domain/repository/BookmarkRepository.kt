package `in`.iot.lab.kritique.domain.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.iot.lab.kritique.domain.models.faculty.RemoteFaculty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local, on-device bookmark store.
 *
 * Bookmarked faculties are kept in memory as the source of truth (so the UI updates
 * instantly) and persisted as JSON in SharedPreferences (so they survive app restarts).
 * No backend is involved — bookmarks live entirely on the device.
 */
@Singleton
class BookmarkRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("kritique_bookmarks", Context.MODE_PRIVATE)
    private val gson = Gson()

    // In-memory source of truth, seeded from disk on first access.
    private val _bookmarkedFaculties = MutableStateFlow(loadFromDisk())

    /** Full faculty objects rendered on the Bookmark screen. */
    val bookmarkedFaculties: StateFlow<List<RemoteFaculty>> = _bookmarkedFaculties.asStateFlow()

    /** Bookmarked faculty IDs — drives the filled/outlined bookmark icon. */
    val bookmarkedIds: Flow<Set<String>> =
        _bookmarkedFaculties.map { list -> list.map { it.id }.toSet() }

    fun isBookmarked(id: String): Boolean =
        _bookmarkedFaculties.value.any { it.id == id }

    /** Add the faculty if not bookmarked, remove it if already bookmarked. */
    fun toggle(faculty: RemoteFaculty) {
        val current = _bookmarkedFaculties.value
        val updated = if (current.any { it.id == faculty.id }) {
            current.filter { it.id != faculty.id }
        } else {
            listOf(faculty) + current
        }
        save(updated)
    }

    /** Remove a faculty by id (used by the Bookmark screen's remove button). */
    fun remove(facultyId: String) {
        save(_bookmarkedFaculties.value.filter { it.id != facultyId })
    }

    private fun save(list: List<RemoteFaculty>) {
        _bookmarkedFaculties.value = list
        prefs.edit().putString(KEY_BOOKMARKS, gson.toJson(list)).apply()
    }

    private fun loadFromDisk(): List<RemoteFaculty> {
        val json = prefs.getString(KEY_BOOKMARKS, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<RemoteFaculty>>() {}.type
            gson.fromJson<List<RemoteFaculty>>(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private companion object {
        const val KEY_BOOKMARKS = "bookmarked_faculties"
    }
}
