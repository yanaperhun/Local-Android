package com.local.app.domain.login.interactors

import com.local.app.api.requests.SNAuthRequest
import com.local.app.data.login.AuthProvider
import com.local.app.data.login.LoginRepository
import com.local.app.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class SocNetAuthInteractor @Inject constructor(val profileRepository: ProfileRepository,
                                               val loginRepository: LoginRepository) {

    fun login(token: String, authProvider: AuthProvider): Completable {
        return loginRepository
            .loginBySocNetworks(SNAuthRequest(token, authProvider.title))
            .flatMap { profileRepository.getProfile() }
            .ignoreElement()
    }

}