package com.local.app.domain.profile

import com.local.app.data.User
import com.local.app.domain.profile.interactors.GetProfileInteractor
import io.reactivex.Single
import javax.inject.Inject

class ProfileDomainFacade @Inject constructor(private val getProfileInteractor: GetProfileInteractor) {

    fun getProfile(): Single<User> {
        return getProfileInteractor.getProfile()
    }

    fun isProfileLoaded(): Boolean {
        return getProfileInteractor.isProfileLoaded()
    }

}