package `in`.iot.lab.teacherreview.core.utils

import `in`.iot.lab.teacherreview.BuildConfig
import `in`.iot.lab.teacherreview.core.utils.Constants.BASE_URL
import `in`.iot.lab.teacherreview.core.utils.Constants.LOGIN_AUTHENTICATION_ENDPOINT
import `in`.iot.lab.teacherreview.core.utils.Constants.TEACHER_LIST_ENDPOINT

/**
 * @property Constants is a class which provides all the ENDPOINTS related to the APP and keeps the
 * BASE URL for the App backend DATA
 *
 * @property BASE_URL this is the Base Url of the App
 * @property LOGIN_AUTHENTICATION_ENDPOINT this endpoint checks the Login Data given by the User and helps
 * in logging the User inside the App. It also provides the User Data like his accessToken which is
 * needed for all the other network calls inside the APP
 * @property TEACHER_LIST_ENDPOINT This is the endpoint which accepts the teacher List Api Call
 * @property
 */
object Constants {
    const val BASE_URL = BuildConfig.BASE_URL
    const val LOGIN_AUTHENTICATION_ENDPOINT = "authenticate"
    const val TEACHER_LIST_ENDPOINT = "faculties"
    const val REVIEWS_ENDPOINT = "reviews"

    const val ITEMS_PER_PAGE = 10
    const val PREFETCH_DISTANCE = 3
}