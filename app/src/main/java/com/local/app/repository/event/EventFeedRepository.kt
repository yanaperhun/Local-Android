package com.local.app.repository.event

import com.local.app.api.RetrofitClient
import com.local.app.data.event.Event
import com.local.app.data.Photo
import io.reactivex.Single
import javax.inject.Inject

class EventFeedRepository @Inject constructor(private var client: RetrofitClient) {
    private var events: List<Event> = emptyList()

    fun loadFeed(): Single<List<Event>> {
        return client.api
            .load()
            .map { it.data }
            .doOnSuccess { events = it }
    }

    fun getImagesByEventId(eventId: Long): Single<List<Photo>> {
        for (event in events) {
            if (event.id == eventId) return Single.just(event.pictures)
        }
        return Single.error(Throwable("Images for event id $eventId are not found"))
    }

    fun getEventById(id: Long): Single<Event> {
        for (e in events) {
            if (e.id == id) {
                return Single.just(e)
            }
        }
        return Single.error(Throwable("Event not found"))
    }

}