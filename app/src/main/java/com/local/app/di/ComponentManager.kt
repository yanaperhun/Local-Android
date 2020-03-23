package com.local.app.di

import com.local.app.di.feed.FeedComponent
import com.local.app.di.feed.FeedModule

class ComponentManager {

    var appComponent: AppComponent? = null

    init {
        appComponent = DaggerAppComponent.create()
    }

    var feedComponent: FeedComponent? = null

    public fun plusFeedComponent(): FeedComponent? {
        if (feedComponent == null) {
            feedComponent = appComponent?.plusFeedComponent(FeedModule())
        }
        return feedComponent
    }

    fun clearFeedComponent() {
        feedComponent = null
    }

}