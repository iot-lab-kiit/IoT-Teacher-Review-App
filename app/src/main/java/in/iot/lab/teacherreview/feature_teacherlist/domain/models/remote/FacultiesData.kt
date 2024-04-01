package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote

import com.google.gson.annotations.SerializedName

/**
 * This Model is the Structure of the Array of Faculty Data returned by the Server
 *
 * @param totalFaculties This is the total Number of faculties Present
 * @param fetchLimit This is the limit amount we can fetch at a time
 * @param fetchSkip This is the amount of teachers we should Skip
 * @param individualFacultyData This is the Data of the Individual Faculty
 */
data class FacultiesData(
    @SerializedName("total")
    val totalFaculties: Int = 0,
    @SerializedName("limit")
    val fetchLimit: Int = 0,
    @SerializedName("skip")
    val fetchSkip: Int = 0,
    @SerializedName("data")
    val individualFacultyData: List<IndividualFacultyData>? = null
)