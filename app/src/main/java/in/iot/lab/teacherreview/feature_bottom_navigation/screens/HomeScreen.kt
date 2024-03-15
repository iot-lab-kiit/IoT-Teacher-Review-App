package `in`.iot.lab.teacherreview.feature_bottom_navigation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomBar
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavOptions
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.HomeNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomMenu = BottomNavOptions.menuItems
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding()),
        ) {
            HomeNavGraph(navController = navController)
        }
    }
}