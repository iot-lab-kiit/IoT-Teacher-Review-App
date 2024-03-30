package `in`.iot.lab.teacherreview.feature_bottom_navigation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomBar
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.BottomNavOptions
import `in`.iot.lab.teacherreview.feature_bottom_navigation.navigation.HomeNavGraph
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.ProfileScreenViewModel

@Composable
internal fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val profileVm: ProfileScreenViewModel = hiltViewModel()
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
                .padding(it),
        ) {
            HomeNavGraph(navController = navController,
                profileVm = profileVm)
        }
    }
}