package com.local.app.presentation.viewmodel.event.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.event.create.EventRaw
import com.local.app.domain.event.create.CreateEventInteractor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CreateEventViewModel(application: Application) : AndroidViewModel(application) {

    private val disposable: CompositeDisposable = CompositeDisposable()
    var eventCreationState: MutableLiveData<EventCreationState> = MutableLiveData()

    @Inject
    lateinit var createEventInteractor: CreateEventInteractor

    init {
        getApplication<LocalApp>().daggerManager.plusCreateEventComponent()
        getApplication<LocalApp>().daggerManager.createEventComponent?.inject(this)
    }

    fun eventBuilder() : EventRaw.Builder {
        return createEventInteractor.eventBuilder()
    }

    override fun onCleared() {
        getApplication<LocalApp>().daggerManager.clearCreateEventComponent()
        super.onCleared()
    }

}