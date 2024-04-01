package `in`.iot.lab.teacherreview.feature_teacherlist.utils

import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.AddReviewApiState.*


/**
 * This sealed interface contains all the States of the Student Review post Request in a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in fetching Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed interface AddReviewApiState {
    data object Initialized : AddReviewApiState
    data object Loading : AddReviewApiState
    class Success(val reviewData: ReviewPostData) : AddReviewApiState
    class Failure(val errorMessage: String) : AddReviewApiState
}