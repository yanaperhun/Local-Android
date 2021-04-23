package com.local.app.domain.feed

import com.local.app.data.event.Event
import com.local.app.repository.event.EventFeedRepository
import io.reactivex.Single
import javax.inject.Inject

class LoadFeedInteractor @Inject constructor(var repository: EventFeedRepository) {

    fun loadFeed(): Single<List<Event>> {
        return repository.loadFeed()
    }

    fun isFeedEmpty() : Boolean{
        return repository.isFeedEmpty()
    }
}