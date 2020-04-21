package com.local.app.domain.feed

import com.local.app.data.Event
import com.local.app.repository.EventFeedRepository
import io.reactivex.Single
import javax.inject.Inject

class LoadFeedInteractor @Inject constructor(var repository: EventFeedRepository) {

    fun execute(): Single<List<Event>> {
        return repository.loadFeed()
    }
}