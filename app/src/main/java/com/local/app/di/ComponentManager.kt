package com.local.app.di

import android.content.Context
import com.local.app.di.event.create.CreateEventComponent
import com.local.app.di.event.create.CreateEventModule
import com.local.app.di.feed.FeedComponent
import com.local.app.di.feed.FeedModule
import com.local.app.di.login.LoginComponent
import com.local.app.di.login.LoginModule
import com.local.app.di.main.AppComponent
import com.local.app.di.main.AppModule
import com.local.app.di.main.DaggerAppComponent
import com.local.app.di.user.list.EventListComponent
import com.local.app.di.user.list.EventListModule

class ComponentManager(context: Context) {

    private var appComponent: AppComponent? = null

    init {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context))
            .build()
    }

    var feedComponent: FeedComponent? = null
    var loginComponent: LoginComponent? = null
    var createEventComponent: CreateEventComponent? = null
    var eventListComponent: EventListComponent? = null

    fun plusCreateEventComponent(): CreateEventComponent? {
        if (createEventComponent == null) {
            createEventComponent = appComponent?.plusCreateEventFragment(CreateEventModule())
        }
        return createEventComponent
    }

    fun clearCreateEventComponent() {
        createEventComponent = null
    }

    fun plusEventListComponent(): EventListComponent? {
        if (eventListComponent == null) {
            eventListComponent = appComponent?.plusEventListComponent(EventListModule())
        }
        return eventListComponent
    }

    fun clearEventListComponent() {
        eventListComponent = null
    }

    fun plusFeedComponent(): FeedComponent? {
        if (feedComponent == null) {
            feedComponent = appComponent?.plusFeedComponent(FeedModule())
        }
        return feedComponent
    }

    fun clearFeedComponent() {
        feedComponent = null
    }

    fun plusLoginComponent(): LoginComponent? {
        if (loginComponent == null) {
            loginComponent = appComponent?.plusLoginComponent(LoginModule())
        }
        return loginComponent
    }

    fun cleaLoginComponent() {
        loginComponent = null
    }
}