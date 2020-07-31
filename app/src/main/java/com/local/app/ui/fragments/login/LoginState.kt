package com.local.app.ui.fragments.login

import com.local.app.data.AppException

sealed class LoginState {

    class ERROR(val error: AppException) : LoginState()
    object LOADING : LoginState()
    object SUCCESS : LoginState()
}