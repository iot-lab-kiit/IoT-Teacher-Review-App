package `in`.iot.lab.kritique.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.network.paging.AppPagingSource
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getFlowState
import `in`.iot.lab.network.utils.NetworkUtil.getUnitFlowState
import `in`.iot.lab.kritique.data.remote.UserApiService
import `in`.iot.lab.kritique.domain.models.common.AccessTokenBody
import `in`.iot.lab.kritique.domain.models.review.PostReviewBody
import `in`.iot.lab.kritique.domain.models.review.RemoteReviewHistoryResponse
import `in`.iot.lab.kritique.domain.models.user.RemoteUser
import `in`.iot.lab.kritique.domain.repository.UserRepo
import `in`.iot.lab.kritique.utils.Constants
import `in`.iot.lab.kritique.utils.Constants.PAGE_LIMIT
import `in`.iot.lab.kritique.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * This class is the Repository Class for the User Schema and contains all the Data Related Database
 * communication for the [RemoteUser] entity.
 */
class UserRepoImpl @Inject constructor(
    private val apiService: UserApiService,
    private val auth: FirebaseAuth
) : UserRepo {

    override fun isUserLoggedIn(): Boolean = auth.currentUser != null


    /**
     * This function logs in the user using the [FirebaseAuth] token or creates a User in the
     * database if not present.
     *
     * @param authCredential This is the Google Auth Credential which contains the data of the user.
     */
    override suspend fun loginUser(authCredential: AuthCredential): Flow<ResponseState<Unit>> {
        return withContext(Dispatchers.IO) {
            getFlowState(
                onFailure = { auth.signOut() }
            ) {

                auth.signInWithCredential(authCredential).await().user
                    ?: throw (Exception("Google Login Failed"))

                // Get the token from the user
                val accessToken = getUserToken()

                // Response of the post request from the backend
                apiService.loginUser(AccessTokenBody(accessToken))
            }
        }
    }


    /**
     * This function logs out the user from the App.
     */
    override suspend fun logOutUser(): Flow<ResponseState<Unit>> {
        return withContext(Dispatchers.IO) {
            getUnitFlowState {
                auth.signOut()
                if (auth.currentUser != null)
                    throw Exception("Failed to log out user from Firebase")
            }
        }
    }


    override suspend fun getUserData(): Flow<ResponseState<RemoteUser>> {
        return withContext(Dispatchers.IO) {
            getFlowState {
                val token = getUserToken()
                apiService.getUserData(AccessTokenBody(token))
            }
        }
    }


    override suspend fun deleteUserData(): Flow<ResponseState<Unit>> {
        return withContext(Dispatchers.IO) {
            getFlowState(
                onSuccess = { auth.signOut() }
            ) {
                val token = getUserToken()
                val userUid = getUserUid()
                apiService.deleteUserData(
                    authToken = token,
                    userUid = userUid
                )
            }
        }
    }


    override suspend fun getUserUid(): String = auth.currentUser?.uid ?: "Invalid Uid"


    override suspend fun getUserToken(): String {
        return try {
            "Bearer ${auth.currentUser?.getIdToken(false)?.await()?.token ?: "No Token Found"}"
        } catch (e: Exception) {
            "No Token Found"
        }
    }

    override suspend fun getReviewHistory(): Flow<PagingData<RemoteReviewHistoryResponse>> {
        val authToken = getUserToken()
        val userUid = getUserUid()
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = Constants.PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                AppPagingSource(
                    request = {
                        apiService.getReviewHistory(
                            authToken = authToken,
                            userUid = userUid,
                            limit = PAGE_LIMIT,
                            page = it.key ?: 0
                        )
                    }
                )
            }
        ).flow
    }

    override suspend fun postUserReview(postData: PostReviewBody): Flow<ResponseState<Unit>> {
        return withContext(Dispatchers.IO) {
            getFlowState {

                val token = getUserToken()
                apiService.postUserReview(
                    authToken = token,
                    postData = postData
                )
            }
        }
    }

    override suspend fun deleteUserReview(reviewId: String): Flow<ResponseState<Unit>> {
        return withContext(Dispatchers.IO) {
            getFlowState {
                val token = getUserToken()
                apiService.deleteUserReview(
                    authToken = token,
                    reviewId = reviewId
                )
            }
        }
    }
}