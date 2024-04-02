package `in`.iot.lab.teacherreview.domain.repository

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun getUserData(userUid: String): Flow<ResponseState<RemoteUser>>
    suspend fun deleteUserData(userUid: String): Flow<ResponseState<Unit>>
}