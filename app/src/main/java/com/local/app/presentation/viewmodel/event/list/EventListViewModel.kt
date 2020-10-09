package com.local.app.presentation.viewmodel.event.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.domain.event.list.LoadEventListInteractor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class EventListViewModel(app: Application) : AndroidViewModel(app) {
    @Inject
    lateinit var loadEventsInteractor: LoadEventListInteractor

    val uiState = MutableLiveData<EventListState>()
    private val compositeDisposable = CompositeDisposable()

    init {
        getApplication<LocalApp>().daggerManager.plusEventListComponent()
        getApplication<LocalApp>().daggerManager.eventListComponent?.inject(this)
    }

    fun loadMyEvents() {
        compositeDisposable.add(loadEventsInteractor
                                    .getMyEvents()
                                    .doOnSubscribe { uiState.value = EventListState.Loading(true) }
                                    .doFinally { uiState.value = EventListState.Loading(false) }
                                    .subscribe({ uiState.postValue(EventListState.Success(it)) }, {
                                        uiState.postValue(
                                            EventListState.Error(AppException(it.message)))
                                    }))
    }

    fun loadLikedEvents() {
        compositeDisposable.add(loadEventsInteractor
                                    .getLikedEvents()
                                    .doOnSubscribe { uiState.value = EventListState.Loading(true) }
                                    .doFinally { uiState.value = EventListState.Loading(false) }
                                    .subscribe({ uiState.postValue(EventListState.Success(it)) }, {
                                        uiState.postValue(
                                            EventListState.Error(AppException(it.message)))
                                    }))
    }

    override fun onCleared() {
        getApplication<LocalApp>().daggerManager.clearEventListComponent()
        compositeDisposable.dispose()
        super.onCleared()
    }
}