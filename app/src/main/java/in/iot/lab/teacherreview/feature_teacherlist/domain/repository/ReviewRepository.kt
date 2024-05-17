package `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository

import androidx.paging.PagingData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.remote.ReviewResponse
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun postReview(review: ReviewPostData): Result<ReviewResponse>
    suspend fun getTeacherReviews(facultyId: String): Result<Flow<PagingData<Review>>>
    suspend fun getStudentsReviewHistory(
        studentId: String
    ): Result<Flow<PagingData<Review>>>

    // TODO: To be added in the next release (maybe ?)
    suspend fun deleteReview(reviewId: String): Result<ReviewResponse>
    // suspend fun updateReview(reviewId: String, review: ReviewPostData): Result<ReviewPostData>
}