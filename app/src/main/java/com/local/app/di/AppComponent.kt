package com.local.app.di

import com.local.app.di.feed.FeedComponent
import com.local.app.di.feed.FeedModule
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface AppComponent {

    fun plusFeedComponent(module: FeedModule): FeedComponent
}