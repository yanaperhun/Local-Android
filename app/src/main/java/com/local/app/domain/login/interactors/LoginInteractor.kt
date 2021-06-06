package com.local.app.domain.login.interactors

import com.local.app.data.login.LoginRepository
import com.local.app.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val loginRepository: LoginRepository,
    private val profileRepository: ProfileRepository
) {

    fun login(email: String, pass: String): Completable {
        return loginRepository
            .login(email, pass)
            .flatMap { profileRepository.loadProfileAndSaveInPref() }.ignoreElement()
    }
}