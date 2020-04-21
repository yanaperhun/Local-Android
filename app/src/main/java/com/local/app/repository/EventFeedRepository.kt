package com.local.app.repository

import com.local.app.api.RetrofitClient
import com.local.app.data.Event
import com.local.app.data.Photo
import io.reactivex.Single
import io.reactivex.functions.Consumer
import javax.inject.Inject

class EventFeedRepository @Inject constructor(private var client: RetrofitClient) {
    private var events: List<Event> = emptyList()

    fun loadFeed(): Single<List<Event>> {
        return client.api
            .load()
            .map { it.data }
            .doOnSuccess { events = it }
    }

    fun getImagesByEventId(eventId: Int): List<Photo> {
        return emptyList()
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