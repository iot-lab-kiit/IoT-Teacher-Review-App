package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen

import android.util.Log.d
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder.TeacherListViewModel

@Composable
fun IndividualTeacherControl(
    navController: NavController,
    myViewModel: TeacherListViewModel
) {

    d("Individual Screen", myViewModel.selectedTeacher.toString())
}