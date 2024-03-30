package `in`.iot.lab.teacherreview.data.review.model

import `in`.iot.lab.teacherreview.common.model.RemoteFaculty


/**
 * This data class is the blueprint for the User Schema.
 *
 * @param id This is the id of the user from the Database.
 * @param uid This is the uid from the Firebase.
 * @param name This is the name of the user.
 * @param email This is the email of the user.
 * @param photoUrl This is the profile Photo Url String.
 * @param role This is the Role of the User which can be either { User , Admin }
 * @param status This is the status of the User which can be either { Approved , Not Approved }
 */
data class RemoteUserReviewHistoryResponse(
    val id: String,
    val uid: String,
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    val role: Int?,
    val status: Int?,
    val reviewList: List<UserReviewData>
) {


    /**
     * This data class is the blueprint for the Review Schema.
     *
     * @param id This is the id provided from the Database.
     * @param createdFor This is the [RemoteFaculty] object for which the review is created.
     * @param rating This is the rating given by [RemoteUser].
     * @param feedback This is the feedback given by the [RemoteUser]
     */
    data class UserReviewData(
        val id: String,
        val createdFor: RemoteFaculty?,
        val rating: Double?,
        val feedback: String?
    )
}