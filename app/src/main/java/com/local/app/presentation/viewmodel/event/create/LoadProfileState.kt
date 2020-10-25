package com.local.app.presentation.viewmodel.event.create

import com.local.app.data.AppException
import com.local.app.data.Profile

sealed class LoadProfileState {

    object LOADING : LoadProfileState()
    class ERROR(val error: AppException) : LoadProfileState()
    class SUCCESS(val profile: Profile) : LoadProfileState()
}