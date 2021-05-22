package com.local.app.ui.fragments.event.legacy.list.liked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.local.app.data.event.Event
import io.reactivex.disposables.CompositeDisposable
import org.ifpri.frani.ui.states.SimpleLoadingState
import org.ifpri.frani.utils.SingleLiveEvent

class LikedEventViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val state = SingleLiveEvent<SimpleLoadingState<List<Event>>>()

    init {
//        getApplication<LocalApp>().daggerManager.eventListComponent?.inject(this)
    }
}