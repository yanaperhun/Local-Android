package com.local.app.presentation.viewmodel.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.domain.photo.UploadPhotoInteractor
import com.local.app.domain.profile.interactors.ProfileInteractor
import com.local.app.presentation.viewmodel.event.create.LoadProfileState
import com.local.app.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var profileInteractor: ProfileInteractor
    lateinit var uploadPhotoInteractor: UploadPhotoInteractor
    var loadProfileState: SingleLiveEvent<LoadProfileState> = SingleLiveEvent()


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
                        LoadProfileState.ERROR(AppException(it.message))
                    )
                })
            )
        }

    }

    fun uploadPhoto(fileDir: String) {
        with(compositeDisposable) {
            add(uploadPhotoInteractor
                .uploadPhoto(fileDir, "user_pic")
                .flatMap { profileInteractor.updateUserPhoto(it.hash) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .doOnSubscribe { loadProfileState.postValue(LoadProfileState.LOADING) }
                .subscribe(
                    { loadProfileState.postValue(LoadProfileState.SUCCESS(it)) },
                    {
                        loadProfileState.postValue(
                            LoadProfileState.ERROR(AppException(it.message))
                        )
                    })
            )
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