package `in`.iot.lab.teacherreview.feature_authentication.data.data_source.remote

import `in`.iot.lab.teacherreview.core.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This is a singleton Object which makes the Retrofit Builder which implements all the function
 * inside the [RetrofitApi] Class and provides the object which can be used to access its functions
 */
object RetrofitInstance {

    // Contains the Base Url which gives all the details from the Database
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Variable which can be used to call all the functions of the RetrofitApi interface
    val apiInstance : RetrofitApi by lazy{
        retrofit.create(RetrofitApi::class.java)
    }
}