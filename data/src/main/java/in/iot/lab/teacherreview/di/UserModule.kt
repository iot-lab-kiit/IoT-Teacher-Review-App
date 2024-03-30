package `in`.iot.lab.teacherreview.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.teacherreview.data.user.remote.UserApiService
import `in`.iot.lab.teacherreview.data.user.repo.UserRepo
import `in`.iot.lab.teacherreview.data.user.repo.UserRepoImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {


    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideUserRepo(api: UserApiService): UserRepo {
        return UserRepoImpl(api)
    }
}

