package com.local.app.presentation.viewmodel.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.Event
import com.local.app.domain.event.GetEventInteractor
import com.local.app.domain.feed.LoadFeedInteractor
import com.local.app.repository.EventFeedRepository
import com.local.app.ui.event.state.EventState
import com.local.app.ui.feed.state.FeedState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable: CompositeDisposable = CompositeDisposable()
    var eventState: MutableLiveData<EventState> = MutableLiveData()

    @Inject
    lateinit var getEventsInteractor: GetEventInteractor

    init {
        getApplication<LocalApp>().daggerManager.plusFeedComponent()
        getApplication<LocalApp>().daggerManager.feedComponent?.inject(this)
    }

    fun getEventById(id: Long) {
        disposable.add(getEventsInteractor
                           .execute(id)
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe({ eventState.value = EventState.Success(it) },
                                      { eventState.value = EventState.Error(it) }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}