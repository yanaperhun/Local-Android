package com.local.app.repository.event.create

import com.local.app.data.event.create.EventRaw

interface CreateEventRepository {
    fun getEventBuilder(): EventRaw.Builder
    fun uploadEvent()
}