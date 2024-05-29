package `in`.iot.lab.teacherreview.utils

object Constants {

    const val PAGE_LIMIT = 10

    // Authentication Endpoints
    const val LOGIN_AUTH_ENDPOINT = "authenticate"

    // User Endpoints
    const val USER_FETCH_ENDPOINT = "authenticate"
    const val USER_DELETE_ENDPOINT = "authenticate/{id}"

    // Faculty Endpoints
    const val FACULTY_FETCH_BY_NAME_ENDPOINT = ""
    const val FACULTY_FETCH_ALL_ENDPOINT = "faculties"

    // Review Endpoints
    const val FACULTY_REVIEW_FETCH_ENDPOINT = "reviews/{id}"
    const val USER_REVIEW_HISTORY_ENDPOINT = "reviews/{id}/history"
    const val USER_POST_REVIEW_ENDPOINT = "reviews"
}