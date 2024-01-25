package `in`.iot.lab.teacherreview

import android.app.Application
import `in`.iot.lab.teacherreview.core.utils.UserUtils

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UserUtils.init(this)
    }
}