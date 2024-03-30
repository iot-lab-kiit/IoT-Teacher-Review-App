package `in`.iot.lab.teacherreview.data.faculty.repo

import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.common.model.RemoteFaculty
import kotlinx.coroutines.flow.Flow

interface FacultyRepo {
    suspend fun getTeacherList(): Flow<ResponseState<List<RemoteFaculty>>>
    suspend fun getTeacherByName(teacherName: String): Flow<ResponseState<List<RemoteFaculty>>>
}