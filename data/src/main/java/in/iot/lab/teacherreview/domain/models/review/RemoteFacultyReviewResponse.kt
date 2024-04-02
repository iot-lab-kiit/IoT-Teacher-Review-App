package `in`.iot.lab.teacherreview.domain.models.review

import `in`.iot.lab.teacherreview.domain.models.user.RemoteUser

/**
 * This data class is the blueprint for the Faculty Schema.
 *
 * @param id This is the id of the user from the Database.
 * @param name This is the name of the Faculty.
 * @param experience This is the experience of the Faculty.
 * @param photoUrl This is the profile Photo Url String.
 * @param avgRating This is the average rating of the faculty.
 * @param totalRating This is the total rating of the Faculty.
 */
data class RemoteFacultyReviewResponse(
    val id: String,
    val name: String,
    val experience: String?,
    val photoUrl: String?,
    val avgRating: Double?,
    val totalRating: Int?,
    val reviewList: List<RemoteFacultyReview>?
) {


    /**
     * This class is the blueprint for the Review data which will be given for the user.
     *
     * @param id This is the id provided from the Database.
     * @param createdBy This is the [RemoteUser] object which created this review.
     * @param rating This is the rating given by [RemoteUser].
     * @param feedback This is the feedback given by the [RemoteUser]
     */
    data class RemoteFacultyReview(
        val id: String,
        val createdBy: RemoteUser?,
        val rating: Double?,
        val feedback: String?
    )
}