package `in`.iot.lab.network.utils

object NetworkStatusCodes {

    // Success codes
    const val SUCCESSFUL = 200
    const val CREATED = 201
    const val DELETED = 202
    const val UPDATED = 203


    // Data not found codes
    const val USER_NOT_FOUND = 204
    const val REVIEW_NOT_FOUND = 205
    const val FACULTY_NOT_FOUND = 206


    // Invalid request or other error codes
    const val INVALID_REQUEST = 400
    const val TOKEN_REQUIRED = 401
    const val INVALID_TOKEN = 402
    const val UNAUTHORIZED = 403
    const val INTERNAL_SERVER_ERROR = 404
}