package `in`.iot.lab.teacherreview.feature_teacherlist.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.data.remote.ReviewsApi
import `in`.iot.lab.teacherreview.feature_teacherlist.data.remote.TeachersApi
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.ReviewRepositoryImpl
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.TeacherRepositoryImpl
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.ReviewRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository.TeachersRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TeacherModule {

    @Provides
    @Named("loggingInterceptor")
    fun providesLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    @Named("teachersModuleClient")
    fun providesHttpClient(
        @Named("loggingInterceptor") interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    }

    @Provides
    fun providesTeachersApi(
        @Named("teachersModuleClient")
        client: OkHttpClient
    ): TeachersApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TeachersApi::class.java)
    }

    @Provides
    fun providesReviewsApi(
        @Named("teachersModuleClient")
        client: OkHttpClient
    ): ReviewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReviewsApi::class.java)
    }

    @Provides
    @Named("authInterceptor")
    @ViewModelScoped
    suspend fun providesAuthInterceptor(
        authRepository: AuthRepository
    ): Interceptor {
        val token = authRepository.getUserIdToken().getOrNull()
        return Interceptor { chain ->
            Log.i("RetrofitInstance", "intercept: $token")
            val request = chain
                .request()
                .newBuilder()
                .apply {
                    if (token != null) {
                        addHeader("Authorization", token)
                    }
                }
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    fun providesTeacherRepository(impl: TeacherRepositoryImpl): TeachersRepository = impl

    @Provides
    fun providesReviewRepository(impl: ReviewRepositoryImpl): ReviewRepository = impl
}