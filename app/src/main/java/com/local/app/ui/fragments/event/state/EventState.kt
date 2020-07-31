package com.local.app.ui.fragments.event.state

import com.local.app.data.event.Event

sealed class EventState {
    object Loading : EventState()
    class Error(val throwable: Throwable) : EventState()
    class Success(val event: Event) : EventState()
}