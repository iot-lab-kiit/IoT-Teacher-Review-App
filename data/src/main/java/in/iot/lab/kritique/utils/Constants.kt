package `in`.iot.lab.kritique.utils

object Constants {

    internal const val PAGE_LIMIT = 10
    internal const val PAGE_SIZE = 10
    internal const val PREFETCH_DISTANCE = 5

    // Authentication Endpoints
    internal const val LOGIN_AUTH_ENDPOINT = "authenticate"

    // User Endpoints
    internal const val USER_FETCH_ENDPOINT = "authenticate"
    internal const val USER_DELETE_ENDPOINT = "authenticate/{id}"

    // Faculty Endpoints
    internal const val FACULTY_FETCH_BY_NAME_ENDPOINT = "faculties/"
    internal const val FACULTY_FETCH_ALL_ENDPOINT = "faculties"
    internal const val FACULTY_FETCH_BY_ID_ENDPOINT = "faculties/{id}"

    // Review Endpoints
    internal const val FACULTY_REVIEW_FETCH_ENDPOINT = "reviews/{id}"
    internal const val USER_REVIEW_HISTORY_ENDPOINT = "reviews/{id}/history"
    internal const val USER_POST_REVIEW_ENDPOINT = "reviews"
    internal const val USER_DELETE_REVIEW_ENDPOINT = "reviews/{id}"
}