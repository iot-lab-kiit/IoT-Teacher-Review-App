package `in`.iot.lab.review.view.events

sealed class FacultyEvent {

    data object FetchFacultyList : FacultyEvent()
}