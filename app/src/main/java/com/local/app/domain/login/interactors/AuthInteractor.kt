package com.local.app.domain.login.interactors

import com.local.app.data.login.LoginRepository
import io.reactivex.Completable
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val loginRepository: LoginRepository) {

    fun auth(name : String, email : String, pass : String) : Completable {
        return loginRepository.auth(name, email, pass).ignoreElement()
    }
}