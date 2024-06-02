package `in`.iot.lab.teacherreview.domain.repository

import androidx.paging.PagingData
import com.google.firebase.auth.AuthCredential
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.domain.models.review.PostReviewBody
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import kotlinx.coroutines.flow.Flow


interface UserRepo {
    fun isUserLoggedIn(): Boolean
    suspend fun loginUser(authCredential: AuthCredential): Flow<ResponseState<Unit>>
    suspend fun logOutUser(): Flow<ResponseState<Unit>>
    suspend fun getUserData(): Flow<ResponseState<RemoteUser>>
    suspend fun deleteUserData(): Flow<ResponseState<Unit>>
    suspend fun getUserUid(): String
    suspend fun getUserToken(): String
    suspend fun getReviewHistory(): Flow<PagingData<RemoteReviewHistoryResponse>>
    suspend fun postUserReview(postData: PostReviewBody): Flow<ResponseState<Unit>>
    suspend fun deleteUserReview(reviewId: String): Flow<ResponseState<Unit>>
}