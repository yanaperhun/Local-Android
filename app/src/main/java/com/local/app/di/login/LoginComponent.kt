package com.local.app.di.login

import com.local.app.di.scopes.PerLogin
import com.local.app.presentation.viewmodel.main.MainActivityViewModel
import dagger.Subcomponent

@PerLogin
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)

}