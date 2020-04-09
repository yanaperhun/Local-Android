package com.local.app.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.Event
import com.local.app.data.User
import com.local.app.domain.feed.GetEventsInteractor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    var eventsLD: MutableLiveData<List<Event>> = MutableLiveData()

    var callbacks: Callbacks? = null

    @Inject
    lateinit var getEventsInteractor: GetEventsInteractor

    init {
        getApplication<LocalApp>().daggerManager.plusFeedComponent()
        getApplication<LocalApp>().daggerManager.feedComponent?.inject(this)
    }

    fun loadFeed() {

        disposable.add(getEventsInteractor
                           .execute()
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(run { callbacks?.onUserLoaded }, run { callbacks?.onError }))
    }

    interface Callbacks {

        var onUserLoaded: (events: List<Event>) -> Unit

        var onError: (error: Throwable) -> Unit

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}