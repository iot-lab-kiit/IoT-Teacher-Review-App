package `in`.iot.lab.teacherreview.core.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object UserUtils {
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    }
    fun getUserID(): String {
        Log.d("UserUtils", "getUserID: ${sharedPreferences.getString("userId", "")}")
        return sharedPreferences.getString("userId", "") ?: ""
    }
    fun saveUserID(userId: String) {
        Log.d("UserUtils", "saveUserID: $userId")
        return sharedPreferences.edit().putString("userId", userId).apply()
    }
}