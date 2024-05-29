package `in`.iot.lab.review.view.events

sealed class FacultyEvent {

    data object FetchFacultyList : FacultyEvent()
    data class FacultySelected(val facultyId: String) : FacultyEvent()
    data object GetFacultyDetails : FacultyEvent()
    data class SubmitReview(val rating: Double, val feedback: String) : FacultyEvent()
}