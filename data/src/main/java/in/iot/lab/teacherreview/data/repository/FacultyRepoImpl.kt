package `in`.iot.lab.teacherreview.data.repository

import androidx.paging.PagingData
import `in`.iot.lab.network.paging.AppPagingSource
import `in`.iot.lab.network.paging.providePager
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.getResponseState
import `in`.iot.lab.teacherreview.domain.models.faculty.RemoteFaculty
import `in`.iot.lab.teacherreview.data.remote.FacultyApiService
import `in`.iot.lab.teacherreview.domain.models.review.RemoteFacultyReview
import `in`.iot.lab.teacherreview.domain.repository.FacultyRepo
import `in`.iot.lab.teacherreview.domain.repository.UserRepo
import `in`.iot.lab.teacherreview.utils.Constants.PAGE_LIMIT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FacultyRepoImpl @Inject constructor(
    private val apiService: FacultyApiService,
    private val user: UserRepo
) : FacultyRepo {

    override suspend fun getTeacherList(): Flow<PagingData<RemoteFaculty>> {

        val token = user.getUserToken()
        return providePager(
            pagingSourceFactory = AppPagingSource(
                request = {
                    apiService.getFacultyList(
                        authToken = token,
                        limit = PAGE_LIMIT,
                        skip = it.key ?: 0
                    )
                }
            )
        ).flow
    }

    override suspend fun getTeacherByName(teacherName: String): Flow<PagingData<RemoteFaculty>> {
        val token = user.getUserToken()
        return providePager(
            pagingSourceFactory = AppPagingSource(
                request = {
                    apiService.getTeacherByName(
                        authToken = token,
                        teacherName = teacherName,
                        limit = PAGE_LIMIT,
                        skip = it.key ?: 0
                    )
                }
            )
        ).flow
    }

    override suspend fun getFacultyReviewData(
        teacherId: String
    ): Flow<PagingData<RemoteFacultyReview>> {

        val token = user.getUserToken()
        return providePager(
            pagingSourceFactory = AppPagingSource(
                request = {
                    apiService.getFacultyReviewData(
                        authToken = token,
                        facultyId = teacherId,
                        limit = PAGE_LIMIT,
                        skip = it.key ?: 0
                    )
                }
            )
        ).flow
    }

    override suspend fun getFacultyById(facultyId: String): Flow<ResponseState<RemoteFaculty>> {
        return withContext(Dispatchers.IO) {
            getResponseState {
                apiService.getFacultyById(
                    authToken = user.getUserToken(),
                    facultyId = facultyId
                )
            }
        }
    }
}