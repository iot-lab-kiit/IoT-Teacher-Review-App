package `in`.iot.lab.teacherreview.common.model


/**
 * This data class is the blueprint for the Review Schema.
 *
 * @param id This is the id provided from the Database.
 * @param createdBy This is the [RemoteUser] object which created this review.
 * @param createdFor This is the [RemoteFaculty] object for which the review is created.
 * @param rating This is the rating given by [RemoteUser].
 * @param feedback This is the feedback given by the [RemoteUser]
 */
data class RemoteReview(
    val id: String,
    val createdBy: RemoteUser?,
    val createdFor: RemoteFaculty?,
    val rating: Double?,
    val feedback: String?
)