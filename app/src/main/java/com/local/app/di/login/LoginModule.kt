package com.local.app.di.login

import com.local.app.api.RetrofitClient
import com.local.app.data.login.LoginRepository
import com.local.app.data.login.LoginRepositoryImpl
import com.local.app.di.scopes.PerLogin
import com.retail.core.prefs.PrefUtils
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    @PerLogin
    fun provideLoginRep(retrofitClient: RetrofitClient, prefUtils: PrefUtils): LoginRepository {
        return LoginRepositoryImpl(retrofitClient, prefUtils)
    }
}