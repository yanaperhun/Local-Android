package com.local.app.di.main

import android.content.ContentResolver
import android.content.Context
import com.local.app.api.RetrofitClient
import com.local.app.data.login.LoginRepository
import com.local.app.data.login.LoginRepositoryImpl
import com.local.app.domain.login.LoginDomainFacade
import com.local.app.domain.login.interactors.AuthInteractor
import com.local.app.domain.login.interactors.LoginInteractor
import com.local.app.pref.PrefUtils
import com.local.app.repository.ProfileRepository
import com.local.app.repository.ProfileRepositoryImpl
import com.local.app.repository.photo.UploadPhotoRepository
import com.local.app.repository.photo.UploadPhotoRepositoryImpl
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

    @Provides
    @Singleton
    fun provideLoginRep(retrofitClient: RetrofitClient, prefUtils: PrefUtils): LoginRepository {
        return LoginRepositoryImpl(retrofitClient, prefUtils)
    }

    @Provides
    @Singleton
    fun provideUploadPhotoRep(retrofitClient: RetrofitClient): UploadPhotoRepository {
        return UploadPhotoRepositoryImpl(retrofitClient)
    }


    @Provides
    @Singleton
    fun provideLoginFacade(loginInteractor: LoginInteractor, authInteractor: AuthInteractor): LoginDomainFacade {
        return LoginDomainFacade(loginInteractor, authInteractor)
    }
}