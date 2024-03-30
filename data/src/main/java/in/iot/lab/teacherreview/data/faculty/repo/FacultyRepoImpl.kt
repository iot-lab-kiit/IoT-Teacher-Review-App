package `in`.iot.lab.teacherreview.data.faculty.repo

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.common.model.RemoteFaculty
import `in`.iot.lab.teacherreview.data.faculty.remote.FacultyApiService
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