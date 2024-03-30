package `in`.iot.lab.teacherreview.data.review.repo

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.common.model.RemoteReview
import `in`.iot.lab.teacherreview.data.review.model.RemoteFacultyReviewResponse
import `in`.iot.lab.teacherreview.data.review.model.RemoteUserReviewHistoryResponse
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