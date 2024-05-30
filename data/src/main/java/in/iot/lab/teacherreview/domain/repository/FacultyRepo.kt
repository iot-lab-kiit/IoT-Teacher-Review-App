package `in`.iot.lab.teacherreview.domain.repository

import androidx.paging.PagingData
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReviewResponse
import kotlinx.coroutines.flow.Flow

interface FacultyRepo {
    suspend fun getTeacherList(): Flow<PagingData<RemoteFaculty>>
    suspend fun getTeacherByName(teacherName: String): Flow<PagingData<RemoteFaculty>>
    suspend fun getFacultyReviewData(
        teacherId: String
    ): Flow<ResponseState<RemoteFacultyReviewResponse>>
}