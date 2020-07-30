package com.local.app.domain.profile

import com.local.app.data.User
import com.local.app.data.login.AuthProvider
import com.local.app.domain.login.interactors.SocNetAuthInteractor
import com.local.app.domain.profile.interactors.GetProfileInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MainActivityDomainFacade @Inject constructor(private val getProfileInteractor: GetProfileInteractor,
private val socNetAuthInteractor: SocNetAuthInteractor) {

    fun getProfile(): Single<User> {
        return getProfileInteractor.getProfile()
    }

    fun loginBySocNetworks(token: String, authProvider: AuthProvider): Completable {
        return socNetAuthInteractor.login(token, authProvider)
    }



    fun isProfileLoaded(): Boolean {
        return getProfileInteractor.isProfileLoaded()
    }

}