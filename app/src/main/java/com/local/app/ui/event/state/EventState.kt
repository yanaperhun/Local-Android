package com.local.app.ui.event.state

import android.util.EventLog
import com.local.app.data.Event

sealed class EventState {
    object Loading : EventState()
    class Error(val throwable: Throwable) : EventState()
    class Success(val event: Event) : EventState()
}