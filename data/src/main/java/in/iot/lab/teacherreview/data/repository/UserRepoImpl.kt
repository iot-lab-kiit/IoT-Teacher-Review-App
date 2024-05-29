package `in`.iot.lab.teacherreview.data.repository

import androidx.paging.PagingData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.network.paging.AppPagingSource
import `in`.iot.lab.network.paging.providePager
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.data.remote.UserApiService
import `in`.iot.lab.teacherreview.domain.models.common.AccessTokenBody
import `in`.iot.lab.teacherreview.domain.models.review.PostReviewBody
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import `in`.iot.lab.teacherreview.domain.repository.UserRepo
import `in`.iot.lab.teacherreview.utils.Constants.PAGE_LIMIT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


/**
 * This class is the Repository Class for the User Schema and contains all the Data Related Database
 * communication for the [RemoteUser] entity.
 */
class UserRepoImpl @Inject constructor(
    private val apiService: UserApiService,
    private val auth: FirebaseAuth
) : UserRepo {


    /**
     * This function logs in the user using the [FirebaseAuth] token or creates a User in the
     * database if not present.
     *
     * @param authCredential This is the Google Auth Credential which contains the data of the user.
     */
    override suspend fun loginUser(authCredential: AuthCredential): Flow<ResponseState<Unit>> {
        return flow {

            // Loading State
            emit(ResponseState.Loading)

            try {

                // User Data
                val user = auth.signInWithCredential(authCredential).await().user
                if (user == null)
                    emit(ResponseState.Error(Exception("Google Login Failed")))
                else {

                    // Get the token from the user
                    val accessToken = getUserToken()

                    // Response of the post request from the backend
                    val response = apiService.loginUser(AccessTokenBody(accessToken))

                    // Check if the response is successful
                    if (response.isSuccessful)
                        emit(ResponseState.Success(Unit))
                    else {
                        auth.signOut()
                        emit(ResponseState.ServerError)
                    }
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e))
                logOutUser()
            }
        }
    }


    /**
     * This function logs out the user from the App.
     */
    override suspend fun logOutUser(): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            try {
                auth.signOut()
                if (auth.currentUser == null)
                    emit(ResponseState.Success(Unit))
                else
                    emit(ResponseState.Error(java.lang.Exception("Failed to log out user from Firebase")))
            } catch (e: Exception) {
                emit(ResponseState.Error(e))
            }
        }
    }


    override suspend fun getUserData(): Flow<ResponseState<RemoteUser>> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                val token = getUserToken()
                apiService.getUserData(AccessTokenBody(token))
            }
        }
    }


    override suspend fun deleteUserData(): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)
            try {

                val token = getUserToken()
                val userUid = getUserUid()
                val response = apiService.deleteUserData(
                    authToken = token,
                    userUid = userUid
                )

                if (response.isSuccessful) {
                    auth.signOut()
                    emit(ResponseState.Success(Unit))
                } else
                    emit(ResponseState.NoDataFound)

            } catch (exception: IOException) {
                emit(ResponseState.NoInternet)
            } catch (e: Exception) {
                emit(ResponseState.Error(e))
            }
        }
    }


    override suspend fun getUserUid(): String {
        return auth.currentUser?.uid ?: "Invalid Uid"
    }


    override suspend fun getUserToken(): String {
        return auth.currentUser?.getIdToken(false)?.await()?.token ?: "Invalid Token"
    }

    override suspend fun getReviewHistory(): Flow<PagingData<RemoteReviewHistoryResponse>> {
        val authToken = getUserToken()
        val userUid = getUserUid()
        return providePager(
            pagingSourceFactory = AppPagingSource(
                request = {
                    apiService.getReviewHistory(
                        authToken = authToken,
                        userUid = userUid,
                        limit = PAGE_LIMIT,
                        skip = (it.key ?: 0) * 10
                    )
                }
            )
        ).flow
    }

    override suspend fun postUserReview(postData: PostReviewBody): Flow<ResponseState<Unit>> {
        return withContext(Dispatchers.IO) {
            getResponseState {

                val token = getUserToken()
                apiService.postUserReview(
                    authToken = token,
                    postData = postData.copy(createdBy = getUserUid())
                )
            }
        }
    }
}