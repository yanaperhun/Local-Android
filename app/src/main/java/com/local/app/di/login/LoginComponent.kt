package com.local.app.di.login

import com.local.app.di.scopes.PerLogin
import dagger.Subcomponent

@PerLogin
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

//    fun inject(mainActivityViewModel: MainActivityViewModel)
//    fun inject(feedVM: EventsFeedViewModel)

}