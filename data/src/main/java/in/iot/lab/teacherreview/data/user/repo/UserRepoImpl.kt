package `in`.iot.lab.teacherreview.data.user.repo

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.common.model.RemoteUser
import `in`.iot.lab.teacherreview.data.user.remote.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * This class is the Repository Class for the User Schema and contains all the Data Related Database
 * communication for the [RemoteUser] entity.
 */
class UserRepoImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepo {

    override suspend fun getUserData(userUid: String): Flow<ResponseState<RemoteUser>> {
        return flow {
            getResponseState {
                apiService.getUserData(
                    authToken = "",
                    userUid = userUid
                )
            }
        }
    }

    override suspend fun deleteUserData(userUid: String): Flow<ResponseState<Unit>> {
        return flow {
            getResponseState {
                apiService.deleteUserData(
                    authToken = "",
                    userUid = userUid
                )
            }
        }
    }
}