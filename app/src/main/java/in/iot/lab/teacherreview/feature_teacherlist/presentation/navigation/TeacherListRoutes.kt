package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation

import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation.TeacherListRoutes.IndividualTeacherRoute
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.IndividualTeacherControl

/**
 * This contains all the possible Routes from the Teacher List Module
 *
 * @property IndividualTeacherRoute This is the Detail Screen of Individual Teacher
 * which is [IndividualTeacherControl]
 */
sealed class TeacherListRoutes(val route: String) {
    object IndividualTeacherRoute : TeacherListRoutes("teacherScreen")
}