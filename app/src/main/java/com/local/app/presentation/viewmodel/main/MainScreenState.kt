package com.local.app.presentation.viewmodel.main

import com.local.app.data.AppException
import com.local.app.data.Profile

sealed class MainScreenState {

    class ProfileLoaded(val profile: Profile) : MainScreenState()
    class Error(val exception: AppException): MainScreenState()
}