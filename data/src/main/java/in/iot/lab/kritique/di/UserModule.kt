package `in`.iot.lab.kritique.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.iot.lab.kritique.data.remote.UserApiService
import `in`.iot.lab.kritique.domain.repository.UserRepo
import `in`.iot.lab.kritique.data.repository.UserRepoImpl
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
    fun provideUserRepo(api: UserApiService, firebaseAuth: FirebaseAuth): UserRepo {
        return UserRepoImpl(api, firebaseAuth)
    }
}

