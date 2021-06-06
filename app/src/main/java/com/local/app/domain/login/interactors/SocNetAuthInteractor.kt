package com.local.app.domain.login.interactors

import com.local.app.api.requests.SocNetAuthRequest
import com.local.app.data.Profile
import com.local.app.data.login.AuthProvider
import com.local.app.data.login.LoginRepository
import com.local.app.repository.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class SocNetAuthInteractor @Inject constructor(private val profileRepository: ProfileRepository,
                                               private val loginRepository: LoginRepository) {

    fun login(token: String, authProvider: AuthProvider): Single<Profile> {
        return loginRepository
            .loginBySocNetworks(SocNetAuthRequest(token, authProvider.title))
            .flatMap { profileRepository.loadProfileAndSaveInPref() }

    }

}