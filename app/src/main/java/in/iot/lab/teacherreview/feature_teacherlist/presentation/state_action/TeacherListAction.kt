package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state_action

import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData

sealed class TeacherListAction {
    data class GetIndividualTeacherReviews(val teacherId: String) : TeacherListAction()
    data class AddTeacherForNextScreen(val teacher: IndividualFacultyData) : TeacherListAction()
    data object GetTeacherList : TeacherListAction()

}