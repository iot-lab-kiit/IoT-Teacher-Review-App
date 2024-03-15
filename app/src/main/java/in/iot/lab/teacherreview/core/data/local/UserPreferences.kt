package `in`.iot.lab.teacherreview.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
class UserPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.dataStore

    val userId = dataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun deleteUserId() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }
}