package com.local.app.presentation.viewmodel.event.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.data.event.create.EventRaw
import com.local.app.data.photo.PhotoInDir
import com.local.app.domain.event.create.CreateEventInteractor
import com.local.app.domain.photo.UploadPhotoInteractor
import com.local.app.domain.profile.interactors.ProfileInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateEventViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var eventCreationState: MutableLiveData<EventCreationState> = MutableLiveData()
    var photoLoadingState: MutableLiveData<EventCreationState> = MutableLiveData()
    var loadProfileState: MutableLiveData<LoadProfileState> = MutableLiveData()

    @Inject
    lateinit var createEventInteractor: CreateEventInteractor

    @Inject
    lateinit var profileInteractor: ProfileInteractor

    @Inject
    lateinit var uploadPhotoInteractor: UploadPhotoInteractor

    init {
        getApplication<LocalApp>().daggerManager.plusCreateEventComponent()
        getApplication<LocalApp>().daggerManager.createEventComponent?.inject(this)
    }

    fun loadProfile() {
        with(compositeDisposable) {
            add(profileInteractor
                    .loadProfileAndSaveInPref()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.single())
                    .doOnEvent { _, _ -> loadProfileState.postValue(LoadProfileState.LOADING) }
                    .subscribe({ loadProfileState.postValue(LoadProfileState.SUCCESS(it)) }, {
                        loadProfileState.postValue(LoadProfileState.ERROR(AppException(it.message)))
                    }))
        }
    }

    fun getPhotos(): List<PhotoInDir> {
        return uploadPhotoInteractor.getPhotos()
    }

    fun uploadPhoto(fileDir: String) {
        with(compositeDisposable) {
            add(uploadPhotoInteractor
                    .uploadPhoto(fileDir, "event_pic")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.single())
                    .doOnEvent { _, _ -> photoLoadingState.postValue(EventCreationState.LOADING) }
                    .subscribe({ photoLoadingState.postValue(EventCreationState.SUCCESS) }, {
                        photoLoadingState.postValue(
                            EventCreationState.ERROR(AppException(it.message)))
                    }))
        }
    }

    fun createEvent() {
        with(compositeDisposable) {
            add(createEventInteractor
                    .createEvent()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.single())
                    .doOnSubscribe { eventCreationState.postValue(EventCreationState.LOADING) }
                    .subscribe({ eventCreationState.postValue(EventCreationState.SUCCESS) }, {
                        eventCreationState.postValue(
                            EventCreationState.ERROR(AppException(it.message)))
                    }))
        }
    }

    fun eventBuilder(): EventRaw.Builder {
        return createEventInteractor.eventBuilder()
    }

    fun isAddressSet() : Boolean {
        return eventBuilder().eventAddress != null
    }

    override fun onCleared() {
        getApplication<LocalApp>().daggerManager.clearCreateEventComponent()
        compositeDisposable.dispose()
        super.onCleared()
    }

}