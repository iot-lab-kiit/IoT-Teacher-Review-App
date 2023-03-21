package `in`.iot.lab.teacherreview.feature_bottom_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme
import `in`.iot.lab.teacherreview.feature_bottom_navigation.components.AppBar
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomBar
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavOptions
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.HomeNavGraph

class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomAppTheme {
                // Setting the navHost controller for this Class which takes care of the rest
                // of the navigation
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        AppBar(
                            topBarTitle = R.string.app_name,
//                            icon = Icons.Default.ArrowBack,
                            contentDescription = R.string.back
                        )
                    },
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                            bottomMenu = BottomNavOptions.menuItems
                        )
                    },
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                    ) {
                        // Calling the function which haves the navigation graph and route details
                        HomeNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}