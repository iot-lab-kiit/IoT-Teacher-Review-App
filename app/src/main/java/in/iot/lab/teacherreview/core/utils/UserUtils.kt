package `in`.iot.lab.teacherreview.core.utils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

object UserUtils {
    private lateinit var userIdManager: UserIdManager
    fun init(context: Context) {
        userIdManager = UserIdManager(context)
    }

    fun getUserID(): Flow<String?> {
        runBlocking {
            Log.d("UserUtils", "getUserID: ${userIdManager.userId.firstOrNull()}")
        }
        return userIdManager.userId
    }

    suspend fun saveUserID(userId: String) {
        Log.d("UserUtils", "saveUserID: $userId")
        userIdManager.saveUserId(userId)
    }

    suspend fun deleteUserID() {
        userIdManager.deleteUserId()
    }
}