package `in`.iot.lab.network.data

import com.google.gson.annotations.SerializedName

data class CustomResponse<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String? = null
)