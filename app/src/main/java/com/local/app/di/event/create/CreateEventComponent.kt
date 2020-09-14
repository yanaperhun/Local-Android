package com.local.app.di.event.create

import com.local.app.di.scopes.PerCreateEvent
import com.local.app.presentation.viewmodel.event.create.CreateEventViewModel
import dagger.Subcomponent

@PerCreateEvent
@Subcomponent(modules = [CreateEventModule::class])
interface CreateEventComponent {
    fun inject(createEventViewModel: CreateEventViewModel)

}