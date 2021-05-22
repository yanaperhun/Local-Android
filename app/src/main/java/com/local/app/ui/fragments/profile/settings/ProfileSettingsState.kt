package com.local.app.ui.fragments.profile.settings

import com.local.app.data.AppException

sealed class ProfileSettingsState {

    class ERROR(val error: AppException) : ProfileSettingsState()
    object LOADING : ProfileSettingsState()
    object SUCCESS : ProfileSettingsState()
}