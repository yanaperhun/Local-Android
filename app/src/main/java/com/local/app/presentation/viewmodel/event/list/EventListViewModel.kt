package com.local.app.presentation.viewmodel.event.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.domain.event.list.LoadEventListInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EventListViewModel(app: Application) : AndroidViewModel(app) {
    @Inject
    lateinit var loadEventsInteractor: LoadEventListInteractor

    val uiStateMyEvents = MutableLiveData<EventListState>()
    val uiStateLikedEvents = MutableLiveData<EventListState>()
    private val compositeDisposable = CompositeDisposable()

    init {
        getApplication<LocalApp>().daggerManager.plusEventListComponent()
        getApplication<LocalApp>().daggerManager.eventListComponent?.inject(this)
    }

    fun loadMyEvents() {
        compositeDisposable.add(loadEventsInteractor
                                    .getMyEvents()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnSubscribe { uiStateMyEvents.value = EventListState.Loading(true) }
                                    .doFinally { uiStateMyEvents.value = EventListState.Loading(false) }
                                    .subscribe({ uiStateMyEvents.postValue(EventListState.Success(it)) }, {
                                        uiStateMyEvents.postValue(
                                            EventListState.Error(AppException(it.message)))
                                    }))
    }

    fun loadLikedEvents() {
        compositeDisposable.add(loadEventsInteractor
                                    .getLikedEvents()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnSubscribe { uiStateLikedEvents.value = EventListState.Loading(true) }
                                    .doFinally { uiStateLikedEvents.value = EventListState.Loading(false) }
                                    .subscribe({ uiStateLikedEvents.postValue(EventListState.Success(it)) }, {
                                        uiStateLikedEvents.postValue(
                                            EventListState.Error(AppException(it.message)))
                                    }))
    }

    override fun onCleared() {
        getApplication<LocalApp>().daggerManager.clearEventListComponent()
        compositeDisposable.dispose()
        super.onCleared()
    }
}