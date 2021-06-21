package com.local.app.ui.fragments.event.legacy.list.liked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.local.app.data.event.Event
import com.local.app.utils.SimpleLoadingState
import com.local.app.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

class LikedEventViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val state = SingleLiveEvent<SimpleLoadingState<List<Event>>>()

    init {
//        getApplication<LocalApp>().daggerManager.eventListComponent?.inject(this)
    }
}