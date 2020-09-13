package com.local.app.di.event.create

import com.local.app.api.RetrofitClient
import com.local.app.di.scopes.PerCreateEvent
import com.local.app.repository.event.create.CreateEventRepository
import com.local.app.repository.event.create.CreateEventRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class CreateEventModule {

    @Provides
    @PerCreateEvent
    fun provideCreateEventRep(retrofitClient: RetrofitClient) : CreateEventRepository {
        return CreateEventRepositoryImpl(retrofitClient)
    }
}