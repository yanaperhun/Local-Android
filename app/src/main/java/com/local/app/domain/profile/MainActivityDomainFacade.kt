package com.local.app.domain.profile

import com.local.app.data.Profile
import com.local.app.data.login.AuthProvider
import com.local.app.domain.login.interactors.SocNetAuthInteractor
import com.local.app.domain.profile.interactors.ProfileInteractor
import io.reactivex.Single
import javax.inject.Inject

class MainActivityDomainFacade @Inject constructor(private val profileInteractor: ProfileInteractor,
                                                   private val socNetAuthInteractor: SocNetAuthInteractor) {

    fun getProfileAsync(): Single<Profile> {
        return profileInteractor.getProfileAsync()
    }

    fun getProfile(): Profile? {
        return profileInteractor.getProfile()
    }

    fun loginBySocNetworks(token: String, authProvider: AuthProvider): Single<Profile> {
        return socNetAuthInteractor.login(token, authProvider)
    }

    fun isProfileLoaded(): Boolean {
        return profileInteractor.isProfileLoaded()
    }

}