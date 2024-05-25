package `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote


import com.google.gson.annotations.SerializedName

data class Faculty(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("experience")
    val experience: String? = null,
    @SerializedName("photoUrl")
    val photoUrl: String? = null,
    @SerializedName("avgRating")
    val avgRating: Double = 0.0,
    @SerializedName("totalRatings")
    val totalRatings: Int? = null,
    @SerializedName("reviewList")
    val reviewList: List<Review> = emptyList(),
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("__v")
    val v: Int? = null
)