package com.local.app.domain.event.create

import com.local.app.data.event.create.EventRaw
import com.local.app.repository.event.create.CreateEventRepository
import javax.inject.Inject

class CreateEventInteractor @Inject constructor(private val createEventRepository: CreateEventRepository) {

    fun eventBuilder() : EventRaw.Builder {
        return createEventRepository.getEventBuilder()
    }

    fun uploadEvent() {
        createEventRepository.uploadEvent()
    }

}