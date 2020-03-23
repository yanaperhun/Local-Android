package com.local.app.di.feed

import com.local.app.repository.EventFeedRepository
import dagger.Module
import dagger.Provides

@Module
class FeedModule {

    @Provides
    @PerFeed
    fun provideFeedRepository() : EventFeedRepository{
        return EventFeedRepository()
    }
}