package `in`.iot.lab.teacherreview.domain.repository

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun getUserData(): Flow<ResponseState<RemoteUser>>
    suspend fun deleteUserData(): Flow<ResponseState<Unit>>
    suspend fun getUserUid(): String
    suspend fun getUserToken(): String
}