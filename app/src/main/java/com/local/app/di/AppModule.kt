package com.local.app.di

import com.local.app.api.RetrofitClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient() : RetrofitClient {
        return RetrofitClient()
    }

}