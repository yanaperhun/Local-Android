package com.local.app.presentation.viewmodel.event.create

import com.local.app.data.AppException

sealed class EventCreationState {

    object LOADING : EventCreationState()
    class ERROR(val error : AppException) : EventCreationState()
    object SUCCESS : EventCreationState()
}