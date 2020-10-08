package com.local.app.presentation.viewmodel.event.list

import com.local.app.data.AppException
import com.local.app.data.event.Event

sealed class EventListState {

    class Error(val error : AppException) : EventListState()
    object Loading : EventListState()
    class Success(val events: List<Event>) : EventListState()
}