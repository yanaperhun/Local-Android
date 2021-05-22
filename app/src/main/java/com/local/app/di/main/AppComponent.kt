package com.local.app.di.main

import com.local.app.di.event.create.CreateEventComponent
import com.local.app.di.event.create.CreateEventModule
import com.local.app.di.feed.FeedComponent
import com.local.app.di.feed.FeedModule
import com.local.app.di.login.LoginComponent
import com.local.app.di.login.LoginModule
import com.local.app.di.user.list.EventListComponent
import com.local.app.di.user.list.EventListModule
import com.local.app.presentation.viewmodel.profile.ProfileViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
//@Component
interface AppComponent {

    fun plusFeedComponent(module: FeedModule): FeedComponent
    fun plusLoginComponent(module: LoginModule): LoginComponent
    fun plusCreateEventFragment(module: CreateEventModule): CreateEventComponent
    fun plusEventListComponent(module: EventListModule): EventListComponent

    fun plusProfileComponent(module: ProfileModule): ProfileComponent


    fun inject(profileViewModel: ProfileViewModel)
}