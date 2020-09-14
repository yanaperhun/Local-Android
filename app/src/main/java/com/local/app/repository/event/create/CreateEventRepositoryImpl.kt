package com.local.app.repository.event.create

import com.local.app.api.RetrofitClient
import com.local.app.data.event.create.EventRaw

class CreateEventRepositoryImpl(val retrofitClient: RetrofitClient) : CreateEventRepository {

    private var eventRaw: EventRaw = EventRaw()
    private var eventBuilder = EventRaw.Builder()

    override fun getEventBuilder(): EventRaw.Builder {
        return eventBuilder
    }

    override fun uploadEvent() {
    }

}