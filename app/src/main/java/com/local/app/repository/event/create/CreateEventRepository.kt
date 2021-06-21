package com.local.app.repository.event.create

import com.local.app.api.response.EventCreateResponse
import com.local.app.data.event.create.EventRaw
import com.local.app.data.photo.PhotoInDir
import io.reactivex.Single

interface CreateEventRepository {
    fun getEventBuilder(): EventRaw.Builder
    fun createEvent(): Single<EventCreateResponse>
    fun addPhoto(photoInDir: PhotoInDir)
}