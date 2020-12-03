package com.local.app.domain.feed

import com.local.app.data.photo.Photo
import com.local.app.repository.event.EventFeedRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPhotoByEventsInteractor @Inject constructor(var repository: EventFeedRepository) {

    fun execute(eventId: Long): Single<List<Photo>> {
        return repository.getImagesByEventId(eventId)
    }
}