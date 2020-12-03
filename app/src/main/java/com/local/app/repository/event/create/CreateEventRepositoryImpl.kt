package com.local.app.repository.event.create

import com.local.app.api.RetrofitClient
import com.local.app.api.response.EventCreateResponse
import com.local.app.data.event.create.EventRaw
import com.local.app.data.photo.PhotoInDir
import io.reactivex.Single

class CreateEventRepositoryImpl(val retrofitClient: RetrofitClient) :
    CreateEventRepository {

    private var eventBuilder = EventRaw.Builder()

    override fun getEventBuilder(): EventRaw.Builder {
        return eventBuilder
    }

    override fun createEvent(): Single<EventCreateResponse> {
        return retrofitClient.api.createEvent(eventBuilder.build())
    }
//
//    override fun uploadPhoto(file: File): Single<Photo> {
//        return uploadPhotoRepository.uploadImageFile(file)
//    }

    override fun addPhoto(photoInDir: PhotoInDir) {
        eventBuilder.pictures.add(photoInDir)
    }

}