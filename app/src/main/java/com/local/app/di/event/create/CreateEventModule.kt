package com.local.app.di.event.create

import com.local.app.api.RetrofitClient
import com.local.app.di.scopes.PerCreateEvent
import com.local.app.repository.event.create.CreateEventRepository
import com.local.app.repository.event.create.CreateEventRepositoryImpl
import com.local.app.repository.photo.UploadPhotoRepository
import com.local.app.repository.photo.UploadPhotoRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class CreateEventModule {

    @Provides
    @PerCreateEvent
    fun provideCreateEventRep(retrofitClient: RetrofitClient): CreateEventRepository {
        return CreateEventRepositoryImpl(retrofitClient)
    }

    @Provides
    @PerCreateEvent
    fun provideUploadPhotoRep(retrofitClient: RetrofitClient): UploadPhotoRepository {
        return UploadPhotoRepositoryImpl(retrofitClient)
    }

}