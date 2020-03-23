package com.local.app.domain.feed

import com.local.app.data.Photo
import com.local.app.repository.EventFeedRepository
import javax.inject.Inject

class GetPhotoByEventsInteractor @Inject constructor(var repository: EventFeedRepository) {

    fun execute(eventId: Int): List<Photo> {
        return repository.getImagesByEventId(eventId)
    }
}