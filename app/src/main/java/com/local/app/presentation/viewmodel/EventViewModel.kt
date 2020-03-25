package com.local.app.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.local.app.data.Event

class EventViewModel(application: Application) : AndroidViewModel(application) {

    public fun getEventById(id: Int): Event? {
        return null
    }
}