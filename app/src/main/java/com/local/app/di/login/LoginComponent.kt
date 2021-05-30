package com.local.app.di.login

import com.local.app.di.scopes.PerLogin
import com.local.app.presentation.viewmodel.main.MainActivityViewModel
import com.local.app.ui.fragments.login.LoginViewModel
import dagger.Subcomponent

@PerLogin
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(mainActivityViewModel: MainActivityViewModel)
    fun inject(mainActivityViewModel: LoginViewModel)
//    fun inject(mainActivityViewModel: MainActivityViewModel)
//    fun inject(feedVM: EventsFeedViewModel)

}