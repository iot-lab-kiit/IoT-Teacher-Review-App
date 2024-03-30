package `in`.iot.lab.teacherreview.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.teacherreview.data.auth.remote.AuthApiService
import `in`.iot.lab.teacherreview.data.auth.repo.AuthRepo
import `in`.iot.lab.teacherreview.data.auth.repo.AuthRepoImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideAuthRepo(
        firebaseAuth: FirebaseAuth,
        apiService: AuthApiService
    ): AuthRepo {
        return AuthRepoImpl(auth = firebaseAuth, apiService = apiService)
    }
}