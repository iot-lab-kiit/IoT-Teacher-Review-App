package `in`.iot.lab.teacherreview.data.repository

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.data.remote.FacultyApiService
import `in`.iot.lab.teacherreview.domain.repository.FacultyRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FacultyRepoImpl @Inject constructor(
    private val apiService: FacultyApiService
) : FacultyRepo {

    override suspend fun getTeacherList(): Flow<ResponseState<List<RemoteFaculty>>> {
        return flow {
            getResponseState {
                apiService.getFacultyList(authToken = "")
            }
        }
    }

    override suspend fun getTeacherByName(teacherName: String): Flow<ResponseState<List<RemoteFaculty>>> {
        return flow {
            getResponseState {
                apiService.getTeacherByName(
                    authToken = "",
                    teacherName = teacherName
                )
            }
        }
    }
}