package com.local.app.ui.fragments.profile.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.data.Profile
import com.local.app.domain.profile.interactors.ProfileInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileSettingsViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var profileInteractor: ProfileInteractor

    val state = MutableLiveData<ProfileSettingsState>()

    private val _personalDataInfo = MutableLiveData<Profile>()
    val personalDataInfo: LiveData<Profile> get() = _personalDataInfo

    private val compositeDisposable = CompositeDisposable()

    init {
        getApplication<LocalApp>().daggerManager.appComponent?.inject(this)
    }

    public fun loadProfileInfo() {
        if (profileInteractor.isProfileLoaded()) {
            _personalDataInfo.postValue(profileInteractor.getProfile())
        } else {
            with(compositeDisposable) {
                add(
                    profileInteractor
                        .getProfileAsync()
                        .doOnSubscribe { state.value = ProfileSettingsState.LOADING }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.single())
                        .subscribe(
                            {
                                _personalDataInfo.postValue(it)
                                state.value = ProfileSettingsState.SUCCESS
                            },
                            { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
                )
            }
        }
    }

    fun updateUserName(firstName: String, lastName: String) {
        compositeDisposable.add(
            profileInteractor.updateUserName(firstName, lastName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(
                    { _personalDataInfo.postValue(it) },
                    { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
        )
    }

    fun updateUserEmail(email: String) {
        compositeDisposable.add(
            profileInteractor.updateUserEmail(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(
                    { _personalDataInfo.postValue(it) },
                    { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
        )
    }

    fun updateUserPhone(phone: String) {
        compositeDisposable.add(
            profileInteractor.updateUserPhone(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(
                    { _personalDataInfo.postValue(it) },
                    { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
        )
    }

    fun updateUserTelegram(telegram: String) {
        compositeDisposable.add(
            profileInteractor.updateUserTelegram(telegram)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(
                    { _personalDataInfo.postValue(it) },
                    { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
        )
    }

    fun updateUserInstagram(instagram: String) {
        compositeDisposable.add(
            profileInteractor.updateUserInstagram(instagram)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(
                    { _personalDataInfo.postValue(it) },
                    { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
        )
    }

    fun updateUserWhatsApp(phone: String) {
        compositeDisposable.add(
            profileInteractor.updateUserWhatsapp(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(
                    { _personalDataInfo.postValue(it) },
                    { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
        )
    }

    fun updateUserPassword(password: String) {
        compositeDisposable.add(
            profileInteractor.updateUserPassword(password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(
                    { },
                    { state.value = ProfileSettingsState.ERROR(AppException(it.message)) })
        )
    }
}