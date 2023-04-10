package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation

import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.navigation.TeacherListRoutes.*
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.AddRatingScreen
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.screen.IndividualTeacherControl

/**
 * This contains all the possible Routes from the Teacher List Module
 *
 * @property IndividualTeacherRoute This is the Detail Screen of Individual Teacher
 * which is [IndividualTeacherControl]
 * @property AddRatingRoute This is the Add Rating Screen of the teacher which is [AddRatingScreen]
 * @property AddReviewRoute This is the Add Review Screen of the teacher which is [AddReviewRoute]
 */
sealed class TeacherListRoutes(val route: String) {
    object IndividualTeacherRoute : TeacherListRoutes("teacherScreen")
    object AddRatingRoute : TeacherListRoutes("add-rating-route")
    object AddReviewRoute : TeacherListRoutes("add-review-route")
}