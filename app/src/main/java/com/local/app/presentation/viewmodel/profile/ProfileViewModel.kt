package com.local.app.presentation.viewmodel.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.domain.profile.interactors.ProfileInteractor
import com.local.app.presentation.viewmodel.event.create.LoadProfileState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var profileInteractor: ProfileInteractor
    var loadProfileState: MutableLiveData<LoadProfileState> = MutableLiveData()

    init {
        getApplication<LocalApp>().daggerManager.appComponent?.inject(this)
    }

    fun loadProfile() {
        with(compositeDisposable) {
            add(profileInteractor
                    .loadProfileAndSaveInPref()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(io.reactivex.schedulers.Schedulers.single())
                    .doOnEvent { _, _ ->
                        loadProfileState.postValue(LoadProfileState.LOADING)
                    }
                    .subscribe({
                                   loadProfileState.postValue(LoadProfileState.SUCCESS(it))
                               }, {
                                   loadProfileState.postValue(
                                       LoadProfileState.ERROR(AppException(it.message)))
                               }))
        }

    }

    fun logout() {
        profileInteractor.logout()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}