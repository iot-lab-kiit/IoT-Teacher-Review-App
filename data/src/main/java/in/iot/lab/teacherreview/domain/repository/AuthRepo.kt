package `in`.iot.lab.teacherreview.domain.repository

import com.google.firebase.auth.AuthCredential
import `in`.iot.lab.network.state.ResponseState
import kotlinx.coroutines.flow.Flow


interface AuthRepo {
    suspend fun loginUser(authCredential: AuthCredential): Flow<ResponseState<Unit>>
    suspend fun logOutUser(): Flow<ResponseState<Unit>>
}