package `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository

import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.ReviewPostData

interface ReviewRepository {
    suspend fun postReview(review: ReviewPostData): Result<ReviewPostData>
    suspend fun getTeacherReviews(facultyId: String, limitValue: Int): Result<ReviewData>
    suspend fun getStudentsReviewHistory(studentId: String, limitValue: Int): Result<ReviewData>

    // TODO: To be added in the next release (maybe ?)
    // suspend fun deleteReview(reviewId: String): Result<Boolean>
    // suspend fun updateReview(reviewId: String, review: ReviewPostData): Result<ReviewPostData>
}