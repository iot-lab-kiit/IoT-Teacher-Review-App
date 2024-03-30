package `in`.iot.lab.teacherreview.data.user.repo

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.common.model.RemoteUser
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun getUserData(userUid: String): Flow<ResponseState<RemoteUser>>
    suspend fun deleteUserData(userUid: String): Flow<ResponseState<Unit>>
}