package com.local.app.repository.event.create

import com.local.app.data.event.create.EventRaw

class CreateEventRepositoryImpl : CreateEventRepository {

    private lateinit var eventRaw: EventRaw

    init {
        eventRaw = EventRaw()
    }
}