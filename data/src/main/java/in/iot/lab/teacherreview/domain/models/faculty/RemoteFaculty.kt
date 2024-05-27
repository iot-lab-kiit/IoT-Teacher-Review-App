package `in`.iot.lab.teacherreview.domain.models.faculty

import com.google.gson.annotations.SerializedName


/**
 * This data class is the blueprint for the Faculty Schema.
 *
 * @param id This is the id of the user from the Database.
 * @param name This is the name of the Faculty.
 * @param experience This is the experience of the Faculty.
 * @param photoUrl This is the profile Photo Url String.
 * @param avgRating This is the average rating of the faculty.
 * @param totalRating This is the total rating of the Faculty.
 * @param createdAt This is the creation date of the Faculty.
 * @param updatedAt This is the update date of the Faculty.
 * @param v This is the version of the Faculty.
 */
data class RemoteFaculty(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("experience")
    val experience: String?,
    @SerializedName("photoUrl")
    val photoUrl: String?,
    @SerializedName("avgRating")
    val avgRating: Double?,
    @SerializedName("totalRatings")
    val totalRating: Int?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int

)