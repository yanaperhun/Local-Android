package com.local.app.ui.fragments.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.domain.login.LoginDomainFacade
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var loginDomainFacade: LoginDomainFacade

    val state = MutableLiveData<LoginState>()
    private val compositeDisposable = CompositeDisposable()

    init {
        getApplication<LocalApp>().daggerManager.plusFeedComponent()
//        getApplication<LocalApp>().daggerManager.plusLoginComponent()
        getApplication<LocalApp>().daggerManager.appComponent?.inject(this)
    }

    fun login(email: String, pass: String) {
        compositeDisposable.add(loginDomainFacade
                                    .login(email, pass)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .doOnSubscribe { state.value = LoginState.LOADING }
                                    .subscribe({ state.value = LoginState.SUCCESS }, {
                                        state.value = LoginState.ERROR(
                                            AppException(it.message ?: "Login error. Try again"))
                                    }))
    }

    fun auth(name: String, email: String, pass: String) {
        compositeDisposable.add(loginDomainFacade
                                    .auth(name, email, pass)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .doOnSubscribe { state.value = LoginState.LOADING }
                                    .subscribe({ state.value = LoginState.SUCCESS }, {
                                        state.value = LoginState.ERROR(
                                            AppException(it.message ?: "Auth error. Try again"))
                                    }))
    }

}