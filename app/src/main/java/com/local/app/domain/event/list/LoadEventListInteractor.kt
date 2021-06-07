package com.local.app.domain.event.list

import com.local.app.data.event.Event
import com.local.app.data.event.list.EventListRepository
import io.reactivex.Single
import javax.inject.Inject

class LoadEventListInteractor @Inject constructor(private val eventListRepository: EventListRepository) {

    fun getLikedEvents() : Single<List<Event>> {
        return eventListRepository.loadLikedEvents()
    }
    fun getMyEvents() : Single<List<Event>> {
        return eventListRepository.loadMyEvents()
    }
}