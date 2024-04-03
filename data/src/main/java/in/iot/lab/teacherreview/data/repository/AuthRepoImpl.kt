package `in`.iot.lab.teacherreview.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.domain.models.user.toRemoteUser
import `in`.iot.lab.teacherreview.data.remote.AuthApiService
import `in`.iot.lab.teacherreview.domain.repository.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val apiService: AuthApiService
) : AuthRepo {


    override suspend fun loginUser(authCredential: AuthCredential): Flow<ResponseState<Unit>> {
        return flow {
            emit(ResponseState.Loading)

            try {
                val user = auth.signInWithCredential(authCredential).await().user
                if (user == null)
                    emit(ResponseState.Error(Exception("Google Login Failed")))
                else {

                    // User Data to be posted in the Backend Server
                    val postUserData = user.toRemoteUser()

                    // Response of the post request from the backend
                    val response = apiService.loginUser(postUserData)
                    if (response.isSuccessful) {
                        emit(ResponseState.Success(Unit))
                        TODO("Do what you need to do with the Access Token")
                    } else {
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
}