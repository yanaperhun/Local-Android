package com.local.app.di.main

import android.content.ContentResolver
import android.content.Context
import com.local.app.api.RetrofitClient
import com.local.app.repository.ProfileRepository
import com.local.app.repository.ProfileRepositoryImpl
import com.retail.core.prefs.PrefUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideHttpClient(prefUtils: PrefUtils): RetrofitClient {
        return RetrofitClient(prefUtils)
    }

    @Provides
    @Singleton
    fun provideProfile(retrofitClient: RetrofitClient, prefUtils: PrefUtils): ProfileRepository {
        return ProfileRepositoryImpl(retrofitClient, prefUtils)
    }

    @Provides
    @Singleton
    fun providePrefs(context: Context): PrefUtils {
        return PrefUtils(context)
    }

    @Provides
    @Singleton
    fun provideContentResolver(context: Context): ContentResolver {
        return context.contentResolver
    }
}