package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote

import `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote.User

/**
 * This is the Structure of the Each Review Data
 *
 * @param _id This is Auto Generated in the Backend
 * @param review This is the review of the user
 * @param rating This contains the Rating Data of the particular Review
 * @param faculty This contains the data of the Faculty for which review is given
 * @param createdBy This contains the Data of the User who Created the Review
 * @param createdAt The time at which the review was created
 */
data class IndividualReviewData(
    val _id: String = "",
    val review: String? = null,
    val rating: RatingData? = null,
    val faculty: IndividualFacultyData,
    val createdBy: User,
    val createdAt : String
)
