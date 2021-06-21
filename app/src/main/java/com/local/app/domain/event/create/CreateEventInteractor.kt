package com.local.app.domain.event.create

import com.local.app.api.response.EventCreateResponse
import com.local.app.data.event.create.EventRaw
import com.local.app.data.photo.PhotoInDir
import com.local.app.repository.event.create.CreateEventRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CreateEventInteractor @Inject constructor(private val createEventRepository: CreateEventRepository) {

    fun eventBuilder() : EventRaw.Builder {
        return createEventRepository.getEventBuilder()
    }
    fun createEvent()  : Single<EventCreateResponse> {
        return createEventRepository.createEvent()
    }


    fun addPhoto(it: PhotoInDir)  : Completable {
        createEventRepository.addPhoto(it)
        return Completable.complete()
    }

}