package `in`.iot.lab.teacherreview.feature_teacherlist.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.data.paging_source.ReviewsSource
import `in`.iot.lab.teacherreview.feature_teacherlist.data.remote.ReviewsApi
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.IndividualReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val reviewsApi: ReviewsApi
): ReviewRepository {

    private suspend fun getToken() = authRepository.getUserIdToken().getOrDefault("")
    override suspend fun postReview(review: ReviewPostData): Result<ReviewPostData> {
        try {
            val response = reviewsApi.postTeacherReviews(
                    post = review,
                    token = getToken()
            )
            Log.d(TAG, response.toString())
            if (!response.isSuccessful) {
                throw Exception("Error Connecting to the Server")
            }

            // TODO: Maybe cache the response here

            return Result.success(response.body()!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun getTeacherReviews(facultyId: String): Result<Flow<PagingData<IndividualReviewData>>> {
        try {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = Constants.ITEMS_PER_PAGE,
                    prefetchDistance = Constants.PREFETCH_DISTANCE,
                )
            ) {
                ReviewsSource(
                    facultyId = facultyId,
                    authRepository = authRepository,
                    reviewsApi = reviewsApi
                )
            }.flow

            return Result.success(pager)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun getStudentsReviewHistory(
        studentId: String,
        limitValue: Int
    ): Result<ReviewData> {
        try {
            val response = reviewsApi.getStudentReviewHistory(
                studentId = studentId,
                limitValue = limitValue,
                token = getToken()
            )
            Log.d(TAG, response.toString())
            if (!response.isSuccessful) {
                throw Exception("Error Connecting to the Server")
            }

            // TODO: Maybe cache the response here
            var reviewData = response.body()!!

            val sortByDesc = reviewData.individualReviewData?.sortedByDescending {
                it.createdAt
            }
            reviewData = reviewData.copy(individualReviewData = sortByDesc)

            return Result.success(reviewData)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}

private const val TAG = "ReviewRepositoryImpl"