package com.local.app.di.main

import com.local.app.di.feed.FeedComponent
import com.local.app.di.feed.FeedModule
import com.local.app.di.login.LoginComponent
import com.local.app.di.login.LoginModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
//@Component
interface AppComponent {

    fun plusFeedComponent(module: FeedModule): FeedComponent
    fun plusLoginComponent(module: LoginModule): LoginComponent
}