package com.local.app.di

import com.local.app.di.feed.FeedComponent
import com.local.app.di.feed.FeedModule
import com.local.app.di.login.LoginComponent
import com.local.app.di.login.LoginModule
import com.local.app.di.main.AppComponent

class ComponentManager {

    var appComponent: AppComponent? = null

    init {
        appComponent = DaggerAppComponent.create()
    }

    var feedComponent: FeedComponent? = null
    var loginComponent: LoginComponent? = null

    fun plusFeedComponent(): FeedComponent? {
        if (feedComponent == null) {
            feedComponent = appComponent?.plusFeedComponent(FeedModule())
        }
        return feedComponent
    }

    fun clearFeedComponent() {
        feedComponent = null
    }

    public fun plusLoginComponent(): LoginComponent? {
        if (loginComponent == null) {
            loginComponent = appComponent?.plusLoginComponent(LoginModule())
        }
        return loginComponent
    }

    fun cleaLoginComponent() {
        loginComponent = null
    }

}