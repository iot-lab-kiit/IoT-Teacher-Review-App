package `in`.iot.lab.teacherreview.feature_authentication.domain.models.remote

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("uid")
    val uid: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("photoUrl")
    val photoUrl: String? = null,
    @SerializedName("role")
    val role: Role? = Role.USER,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("__v")
    val v: Int? = null
)

enum class Role {
    @SerializedName("user")
    USER,

    @SerializedName("admin")
    ADMIN
}
