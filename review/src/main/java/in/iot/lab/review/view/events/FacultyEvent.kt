package `in`.iot.lab.review.view.events

import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty

sealed class FacultyEvent {

    data object FetchFacultyList : FacultyEvent()
    data class FetchFacultyByName(val name: String) : FacultyEvent()
    data class FacultySelected(val facultyId: String) : FacultyEvent()
    data object GetFacultyDetails : FacultyEvent()
    data class SubmitReview(val rating: Double, val feedback: String) : FacultyEvent()
    data object ResetSubmitState : FacultyEvent()
}