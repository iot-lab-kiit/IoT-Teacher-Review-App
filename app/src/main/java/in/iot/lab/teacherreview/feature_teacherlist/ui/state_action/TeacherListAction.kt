package `in`.iot.lab.teacherreview.feature_teacherlist.ui.state_action

import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Faculty

sealed class TeacherListAction {
    data class GetIndividualTeacherReviews(val teacherId: String) : TeacherListAction()
    data class AddTeacherForNextScreen(val teacher: Faculty) : TeacherListAction()
    data class GetTeacherList(val searchQuery: String? = null) : TeacherListAction()

    data class DeleteReview(val reviewId: String) : TeacherListAction()
}