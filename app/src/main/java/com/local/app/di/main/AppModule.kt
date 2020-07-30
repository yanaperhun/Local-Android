package com.local.app.di.main

import com.local.app.api.RetrofitClient
import com.local.app.repository.ProfileRepository
import com.local.app.repository.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): RetrofitClient {
        return RetrofitClient()
    }

    @Provides
    @Singleton
    fun provideProfile(retrofitClient: RetrofitClient): ProfileRepository {
        return ProfileRepositoryImpl(retrofitClient)
    }
}