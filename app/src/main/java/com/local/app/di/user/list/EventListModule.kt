package com.local.app.di.user.list

import com.local.app.api.RetrofitClient
import com.local.app.data.event.list.EventListRepository
import com.local.app.data.event.list.EventListRepositoryImpl
import com.local.app.di.scopes.PerUser
import dagger.Module
import dagger.Provides

@Module
class EventListModule {

    @Provides
    @PerUser
    fun provideEventListRep(retrofitClient: RetrofitClient) : EventListRepository {
        return EventListRepositoryImpl(retrofitClient)
    }
}