package `in`.iot.lab.teacherreview.data.repository

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.data.remote.FacultyApiService
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse
import `in`.iot.lab.teacherreview.domain.repository.FacultyRepo
import `in`.iot.lab.teacherreview.domain.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacultyRepoImpl @Inject constructor(
    private val apiService: FacultyApiService,
    private val user: UserRepo
) : FacultyRepo {

    override suspend fun getTeacherList(): Flow<ResponseState<List<RemoteFaculty>>> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                val token = user.getUserToken()
                apiService.getFacultyList(authToken = token)
            }
        }
    }

    override suspend fun getTeacherByName(teacherName: String): Flow<ResponseState<List<RemoteFaculty>>> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                val token = user.getUserToken()
                apiService.getTeacherByName(
                    authToken = token,
                    teacherName = teacherName
                )
            }
        }
    }

    override suspend fun getFacultyReviewData(
        teacherId: String
    ): Flow<ResponseState<RemoteFacultyReviewResponse>> {
        return withContext(Dispatchers.IO) {
            getResponseState {

                val token = user.getUserToken()
                apiService.getFacultyReviewData(
                    authToken = token,
                    facultyId = teacherId
                )
            }
        }
    }
}