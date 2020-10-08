package com.local.app.presentation.viewmodel.event.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.domain.event.list.LoadEventListInteractor
import javax.inject.Inject

class EventListViewModel(app : Application) : AndroidViewModel(app) {
    @Inject
    lateinit var loadEventsInteractor: LoadEventListInteractor

    val uiState = MutableLiveData<EventListState>()

    fun loadEvent() {

    }
}