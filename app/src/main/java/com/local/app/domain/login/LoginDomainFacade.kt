package com.local.app.domain.login

import com.local.app.domain.login.interactors.AuthInteractor
import com.local.app.domain.login.interactors.LoginInteractor
import io.reactivex.Completable
import javax.inject.Inject

class LoginDomainFacade @Inject constructor(private val loginInteractor: LoginInteractor,
                                            private val authInteractor: AuthInteractor) {

    fun login(email: String, pass: String): Completable {
        return loginInteractor.login(email, pass)
    }

    fun auth(name: String, email: String, pass: String): Completable {
        return authInteractor.auth(name, email, pass)
    }
}