package `in`.iot.lab.teacherreview.data.repository

import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import `in`.iot.lab.teacherreview.data.remote.UserApiService
import `in`.iot.lab.teacherreview.domain.models.common.AccessTokenBody
import `in`.iot.lab.teacherreview.domain.repository.UserRepo
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

    override suspend fun getUserData(): Flow<ResponseState<RemoteUser>> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                val tokenResult = auth.currentUser?.getIdToken(false)?.await()
                val token = tokenResult?.token ?: "Not Valid"
                apiService.getUserData(AccessTokenBody(token))
            }
        }
    }

    override suspend fun deleteUserData(userUid: String): Flow<ResponseState<Unit>> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.deleteUserData(
                    authToken = "",
                    userUid = userUid
                )
            }
        }
    }
}