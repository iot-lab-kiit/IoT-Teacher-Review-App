package `in`.iot.lab.teacherreview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.navigation.TeacherReviewNavGraph
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CustomAppTheme {
                val navController = rememberNavController()
                TeacherReviewNavGraph(
                    isUserLoggedIn = repository.isUserSignedIn(),
                    navHostController = navController
                )
            }
        }
    }
}