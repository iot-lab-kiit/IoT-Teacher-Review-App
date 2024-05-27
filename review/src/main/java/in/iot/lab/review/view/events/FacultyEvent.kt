package `in`.iot.lab.review.view.events

sealed class FacultyEvent {

    data object FetchFacultyList : FacultyEvent()
    data class FacultySelected(val facultyId: String) : FacultyEvent()
}