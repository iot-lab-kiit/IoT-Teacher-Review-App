package `in`.iot.lab.kritique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import `in`.iot.lab.design.theme.*
import `in`.iot.lab.kritique.domain.repository.UserRepo
import `in`.iot.lab.kritique.view.navigation.MainNavGraph
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // User Repo
    @Inject
    lateinit var user: UserRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = GradientTop.toArgb()
        setContent {
            CustomAppTheme {
                val navController = rememberNavController()
                MainNavGraph(
                    isUserLoggedIn = user.isUserLoggedIn(),
                    navHostController = navController
                )
            }
        }
    }
}