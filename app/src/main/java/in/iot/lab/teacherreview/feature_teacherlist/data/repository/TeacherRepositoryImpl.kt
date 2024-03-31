package `in`.iot.lab.teacherreview.feature_teacherlist.data.repository

import android.util.Log
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.data.remote.TeachersApi
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.FacultiesData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.TeachersRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.AddReviewApiState
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.GetHistoryApiCallState
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.IndividualTeacherReviewApiCall
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState
import javax.inject.Inject

class TeacherRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val teachersApi: TeachersApi
) : TeachersRepository {
    private suspend fun getToken() = authRepository.getUserIdToken().getOrDefault("")

    override suspend fun getAllTeachers(
        limitValue: Int,
        searchQuery: String?
    ): Result<FacultiesData> {
        try {
            val response = teachersApi.getTeacherList(
                token = getToken(),
                limitValue = limitValue,
                facultyName = searchQuery
            )
            Log.d(TAG, response.toString())
            if (!response.isSuccessful) {
                throw Exception("Failed to get teachers list")
            }

            // TODO: Maybe cache the response here

            return Result.success(response.body()!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}

private const val TAG = "TeacherRepositoryImpl"