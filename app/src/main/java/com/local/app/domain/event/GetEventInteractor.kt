package com.local.app.domain.event

import com.local.app.data.event.Event
import com.local.app.repository.event.EventFeedRepository
import io.reactivex.Single
import javax.inject.Inject

class GetEventInteractor @Inject constructor(var repository: EventFeedRepository) {

    fun execute(id : Long): Single<Event> {
        return repository.getEventById(id)
    }
}