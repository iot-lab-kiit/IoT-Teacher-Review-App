package `in`.iot.lab.network.utils

object NetworkStatusCodes {

    // App Error Codes
    const val INTERNET_ERROR = 405

    // Server Error Codes
    const val INTERNAL_SERVER_ERROR = 404

    // Success codes
    const val SUCCESSFUL = 200
    const val CREATED = 201
    const val DELETED = 202
    const val UPDATED = 203

    // Data not found codes
    const val USER_NOT_FOUND = 204
    const val REVIEW_NOT_FOUND = 205
    const val FACULTY_NOT_FOUND = 206
}