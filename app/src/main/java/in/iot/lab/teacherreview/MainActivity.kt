package `in`.iot.lab.teacherreview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.teacherreview.navigation.MainNavGraph
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CustomAppTheme {
                val navController = rememberNavController()
                MainNavGraph(
                    isUserLoggedIn = auth.currentUser != null,
                    navHostController = navController
                )
            }
        }
    }
}