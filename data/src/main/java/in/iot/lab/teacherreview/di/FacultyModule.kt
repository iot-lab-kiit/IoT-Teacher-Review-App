package `in`.iot.lab.teacherreview.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.teacherreview.data.remote.FacultyApiService
import `in`.iot.lab.teacherreview.domain.repository.FacultyRepo
import `in`.iot.lab.teacherreview.data.repository.FacultyRepoImpl
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
    fun provideFacultyRepo(apiService: FacultyApiService): FacultyRepo {
        return FacultyRepoImpl(apiService)
    }
}