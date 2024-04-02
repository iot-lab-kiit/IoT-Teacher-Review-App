package `in`.iot.lab.teacherreview.feature_teacherlist.utils

import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.GetHistoryApiCallState.*

/**
 * This sealed Class contains all the States of the Student Review History Request in a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in fetching Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class GetHistoryApiCallState {
    object Initialized : GetHistoryApiCallState()
    object Loading : GetHistoryApiCallState()
    class Success(val reviewData: ReviewData) : GetHistoryApiCallState()
    class Failure(val errorMessage: String) : GetHistoryApiCallState()
}
