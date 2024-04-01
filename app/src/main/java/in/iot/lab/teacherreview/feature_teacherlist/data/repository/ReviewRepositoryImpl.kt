package `in`.iot.lab.teacherreview.feature_teacherlist.data.repository

import android.util.Log
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.data.remote.ReviewsApi
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.ReviewRepository
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

    override suspend fun getTeacherReviews(facultyId: String, limitValue: Int): Result<ReviewData> {
        try {
            val response = reviewsApi.getIndividualTeacherReviews(
                limitValue = limitValue,
                facultyId = facultyId,
                token = getToken()
            )
            Log.d(TAG, response.toString())
            if (!response.isSuccessful) {
                throw Exception("Error Connecting to the Server")
            }

            // TODO: Maybe cache the response here
            val reviewData = response.body()!!

            val sortedReviews = reviewData.individualReviewData?.sortedByDescending {
                it.createdAt
            }

            val sortedResponse = ReviewData(
                avgAttendanceRating = reviewData.avgAttendanceRating,
                avgMarkingRating = reviewData.avgMarkingRating,
                avgTeachingRating = reviewData.avgTeachingRating,
                total = reviewData.total,
                limit = reviewData.limit,
                skip = reviewData.skip,
                individualReviewData = sortedReviews
            )

            return Result.success(sortedResponse)
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