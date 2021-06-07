package com.local.app.repository.event

import android.util.Log
import com.local.app.api.RetrofitClient
import com.local.app.data.event.Event
import com.local.app.data.photo.PhotoEntity
import io.reactivex.Single

class EventFeedRepository (private var client: RetrofitClient) {
    private var events: List<Event> = emptyList()

    init {
        Log.d("Local", "===>>> init EventFeedRepository")
    }

    fun loadFeed(): Single<List<Event>> {
        return client.api
            .load()
            .map { it.data }
            .doOnSuccess {
                events = it
                Log.d("Local", "===>>>do on success  events: ${events}")
            }
    }

    fun getImagesByEventId(eventId: Long): Single<List<PhotoEntity>> {
        for (event in events) {
            if (event.id == eventId) return Single.just(event.pictures)
        }
        return Single.error(Throwable("Images for event id $eventId are not found"))
    }

    fun getEventById(id: Long): Single<Event> {
        Log.d("Local", "===>>>events: ${events}")
        for (e in events) {
            if (e.id == id) {
                return Single.just(e)
            }
        }
        return client.api.getEvent(id)
    }

    fun isFeedEmpty(): Boolean {
        return events.isNullOrEmpty()
    }

}