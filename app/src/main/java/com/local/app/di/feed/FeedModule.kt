package com.local.app.di.feed

import com.local.app.api.RetrofitClient
import com.local.app.di.scopes.PerFeed
import com.local.app.repository.event.EventFeedRepository
import dagger.Module
import dagger.Provides

@Module
class FeedModule {

    @Provides
    @PerFeed
    fun provideFeedRep(client: RetrofitClient): EventFeedRepository {
        return EventFeedRepository(client)
    }
}