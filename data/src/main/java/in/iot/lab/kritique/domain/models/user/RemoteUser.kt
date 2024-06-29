package `in`.iot.lab.kritique.domain.models.user


import com.google.gson.annotations.SerializedName


/**
 * This data class is the blueprint for the User Schema.
 *
 * @param uid This is the uid from the Firebase.
 * @param name This is the name of the user.
 * @param email This is the email of the user.
 * @param photoUrl This is the profile Photo Url String.
 */
data class RemoteUser(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("anon_name")
    val anonymousName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("photoUrl")
    val photoUrl: String?
)