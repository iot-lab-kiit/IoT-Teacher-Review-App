package `in`.iot.lab.profile.view.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
fun findSemester(rollNumber: String?): String? {

    if (rollNumber == null)
        return null

    val localData = LocalDate.now()

    val joiningYear = rollNumber.substring(0, 2).toInt()
    val currentYear = localData.year.toString().substring(2, 4).toInt()
    val currentMonth = localData.monthValue
    val semester = (currentYear - joiningYear) * 2 + if (currentMonth in 7..12) 1 else 0

    return when (semester) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        else -> "${semester}th"
    } + " Semester"
}