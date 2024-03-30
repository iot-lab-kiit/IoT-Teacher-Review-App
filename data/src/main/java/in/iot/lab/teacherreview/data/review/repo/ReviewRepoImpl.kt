package `in`.iot.lab.teacherreview.data.review.repo

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.common.model.RemoteReview
import `in`.iot.lab.teacherreview.data.review.model.RemoteFacultyReviewResponse
import `in`.iot.lab.teacherreview.data.review.model.RemoteUserReviewHistoryResponse
import `in`.iot.lab.teacherreview.data.review.remote.ReviewApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReviewRepoImpl @Inject constructor(
    private val apiService: ReviewApiService
) : ReviewRepo {
    override suspend fun getFacultyReviewData(
        teacherId: String
    ): Flow<ResponseState<RemoteFacultyReviewResponse>> {
        return flow {
            getResponseState {
                apiService.getFacultyReviewData(
                    authToken = "",
                    teacherId = teacherId
                )
            }
        }
    }

    override suspend fun getUserReviewHistory(
        userUid: String
    ): Flow<ResponseState<RemoteUserReviewHistoryResponse>> {
        return flow {
            getResponseState {
                apiService.getUserReviewHistory(
                    authToken = "",
                    userUid = userUid
                )
            }
        }
    }

    override suspend fun postUserReview(postData: RemoteReview): Flow<ResponseState<Unit>> {
        return flow {
            getResponseState {
                apiService.postUserReview(
                    authToken = "",
                    postData = postData
                )
            }
        }
    }
}