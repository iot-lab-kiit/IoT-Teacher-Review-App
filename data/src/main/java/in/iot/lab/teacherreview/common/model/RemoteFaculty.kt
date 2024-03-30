package `in`.iot.lab.teacherreview.common.model


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
data class RemoteFaculty(
    val id: String,
    val name: String,
    val experience: String?,
    val photoUrl: String?,
    val avgRating: Double?,
    val totalRating: Int?
)