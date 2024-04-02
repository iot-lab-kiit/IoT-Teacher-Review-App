package `in`.iot.lab.teacherreview.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.teacherreview.data.remote.ReviewApiService
import `in`.iot.lab.teacherreview.domain.repository.ReviewRepo
import `in`.iot.lab.teacherreview.data.repository.ReviewRepoImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ReviewModule {

    @Provides
    @Singleton
    fun provideReviewApiService(retrofit: Retrofit): ReviewApiService {
        return retrofit.create(ReviewApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideReviewRepo(apiService: ReviewApiService): ReviewRepo {
        return ReviewRepoImpl(apiService)
    }
}