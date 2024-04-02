package `in`.iot.lab.teacherreview.domain.repository

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReview
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse
import `in`.iot.lab.teacherreview.domain.models.review.RemoteUserReviewHistoryResponse
import kotlinx.coroutines.flow.Flow

interface ReviewRepo {

    suspend fun getFacultyReviewData(
        teacherId: String
    ): Flow<ResponseState<RemoteFacultyReviewResponse>>

    suspend fun getUserReviewHistory(
        userUid: String
    ): Flow<ResponseState<RemoteUserReviewHistoryResponse>>
    suspend fun postUserReview(postData: RemoteReview): Flow<ResponseState<Unit>>
}