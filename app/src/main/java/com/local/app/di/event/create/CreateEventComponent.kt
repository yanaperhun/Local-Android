package com.local.app.di.event.create

import com.local.app.di.scopes.PerCreateEvent
import dagger.Subcomponent

@PerCreateEvent
@Subcomponent(modules = [CreateEventModule::class])
interface CreateEventComponent {

}