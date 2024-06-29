package `in`.iot.lab.kritique.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.kritique.data.remote.FacultyApiService
import `in`.iot.lab.kritique.domain.repository.FacultyRepo
import `in`.iot.lab.kritique.data.repository.FacultyRepoImpl
import `in`.iot.lab.kritique.domain.repository.UserRepo
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FacultyModule {


    @Provides
    @Singleton
    fun provideFacultyApiService(retrofit: Retrofit): FacultyApiService {
        return retrofit.create(FacultyApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideFacultyRepo(apiService: FacultyApiService, userRepo: UserRepo): FacultyRepo {
        return FacultyRepoImpl(apiService = apiService, user = userRepo)
    }
}