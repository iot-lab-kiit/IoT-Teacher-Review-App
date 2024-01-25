package `in`.iot.lab.teacherreview.core.utils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow

object UserUtils {
    private lateinit var userIdManager: UserIdManager
    fun init(context: Context) {
        userIdManager = UserIdManager(context)
    }
    fun getUserID(): Flow<String?> {
        Log.d("UserUtils", "getUserID: ${userIdManager.userId}")
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