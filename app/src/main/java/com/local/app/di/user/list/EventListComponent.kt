package com.local.app.di.user.list

import com.local.app.di.scopes.PerUser
import com.local.app.presentation.viewmodel.event.list.EventListViewModel
import dagger.Subcomponent

@PerUser
@Subcomponent(modules = [EventListModule::class])
interface EventListComponent {
    fun inject(eventListViewModel: EventListViewModel)

}