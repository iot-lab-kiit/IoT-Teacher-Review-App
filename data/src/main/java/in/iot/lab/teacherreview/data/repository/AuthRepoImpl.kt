package `in`.iot.lab.teacherreview.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.data.remote.AuthApiService
import `in`.iot.lab.teacherreview.domain.models.common.AccessTokenBody
import `in`.iot.lab.teacherreview.domain.repository.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


/**
 * This class implements the Authentication Logic for the app.
 *
 * @param auth This is the firebase Auth instance.
 * @param apiService This is the [AuthApiService] api service instance.
 */
class AuthRepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val apiService: AuthApiService
) : AuthRepo {


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
                    val accessToken = user.getIdToken(false).await().token ?: "Invalid"

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
}