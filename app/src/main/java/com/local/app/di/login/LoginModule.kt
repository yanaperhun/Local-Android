package com.local.app.di.login

import com.local.app.api.RetrofitClient
import com.local.app.data.login.LoginRepository
import com.local.app.data.login.LoginRepositoryImpl
import com.local.app.di.scopes.PerLogin
import com.local.app.domain.login.LoginDomainFacade
import com.local.app.domain.login.interactors.AuthInteractor
import com.local.app.domain.login.interactors.LoginInteractor
import com.local.app.pref.PrefUtils
import dagger.Module

@Module
class LoginModule {

//    @Provides
//    @PerLogin
//    fun provideLoginRep(retrofitClient: RetrofitClient, prefUtils: PrefUtils): LoginRepository {
//        return LoginRepositoryImpl(retrofitClient, prefUtils)
//    }
//
//    @Provides
//    @PerLogin
//    fun provideLoginFacade(loginInteractor: LoginInteractor, authInteractor: AuthInteractor): LoginDomainFacade {
//        return LoginDomainFacade(loginInteractor, authInteractor)
//    }
}