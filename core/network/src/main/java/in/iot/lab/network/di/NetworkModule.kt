package `in`.iot.lab.network.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.network.BuildConfig
import `in`.iot.lab.network.di.NetworkModule.provideGson
import `in`.iot.lab.network.di.NetworkModule.provideGsonConverterFactory
import `in`.iot.lab.network.di.NetworkModule.provideOkHttpClient
import `in`.iot.lab.network.di.NetworkModule.provideRetrofit
import `in`.iot.lab.network.di.NetworkModule.providesFirebaseAuth
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * This is the Core DI Module which contains all the core DI elements which should be used by all
 * the other modules.
 *
 * @property provideOkHttpClient Provides okHttpClient for logging
 * @property provideGson provides the GSON object used for serializing and deserializing the JSON
 * @property provideGsonConverterFactory provide the Gson Converter Factory to configure
 * the Gson Object
 * @property provideRetrofit provides the Base Retrofit Object which can be used to create
 * the API service instances.
 * @property providesFirebaseAuth provide the Firebase Auth Object which can be used to authorize
 * the user and check if the user is logged in and so on.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    /**
     * This function is providing the Ok Http Client for logging purposes
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {

        val versionName: String = try {
            context.packageManager
                .getPackageInfo(context.packageName, 0)
                .versionName
        } catch (e: Exception) {
            e.printStackTrace()
            "Version Not Found"
        }

        // Logging Interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        // HTTP Client with bearer token
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("version", versionName)
                    .build()
                chain.proceed(request)
            }
            .build()
    }


    /**
     * This function provides the GSON object used for serializing and deserializing the JSONs
     */
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create()
    }


    /**
     * This function is used to provide the Gson Converter Factory which would be used to configure
     * the Gson Object
     *
     * @param gson Object for serializing and deserializing
     */
    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }


    /**
     * This function provides the Base Retrofit Object which can be used to create the API service
     * instances.
     *
     * @param okHttpClient Client for logging.
     * @param gsonConverterFactory Gson for serialization and deserialization.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()


    /**
     * This function is used to provide the Firebase Auth Object which can be used to authorize the
     * user and check if the user is logged in and so on.
     */
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}