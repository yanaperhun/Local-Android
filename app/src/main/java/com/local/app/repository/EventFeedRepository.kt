package com.local.app.repository

import com.local.app.api.RetrofitClient
import com.local.app.data.Event
import com.local.app.data.Photo
import io.reactivex.Single
import javax.inject.Inject

class EventFeedRepository @Inject constructor(var client: RetrofitClient) {

    fun loadFeed(): Single<List<Event>> {
        return client.api
            .load()
            .map { it.data }
    }

    fun getImagesByEventId(eventId: Int): List<Photo> {
        return emptyList()
    }

}