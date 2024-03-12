package `in`.iot.lab.teacherreview.feature_teacherlist.data.data_source.remote

import `in`.iot.lab.teacherreview.core.utils.Constants.BASE_URL
import `in`.iot.lab.teacherreview.feature_teacherlist.data.data_source.remote.RetrofitInstance.apiInstance
import `in`.iot.lab.teacherreview.feature_teacherlist.data.data_source.remote.RetrofitInstance.retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This is the Retrofit Instance Object which have a retrofit instance which is getting used
 * all around this module in the App
 *
 * @property retrofit This variable is private and contains the Build Features
 * @property apiInstance This is public and contains the object of the class Implemented by the
 * retrofit Library
 */
object RetrofitInstance {

    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    // Contains the Base Url which gives all the details from the Database
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // Variable which can be used to call all the functions of the RetrofitApi interface
    val apiInstance: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }

    // TODO: Use This again when DI is implemented
    /*private val AuthInterceptor = object: Interceptor {

        private var token = Firebase.auth.currentUser?.getIdToken(false)?.result?.token
            ?: throw Exception("Token is null");

        override fun intercept(chain: Interceptor.Chain): Response {
            Log.i("RetrofitInstance", "intercept: $token")
            val request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", token)
                .build()
            return chain.proceed(request)
        }
    }*/
}