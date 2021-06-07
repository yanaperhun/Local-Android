package com.local.app.data.event.list

import com.local.app.api.RetrofitClient
import com.local.app.data.event.Event
import io.reactivex.Single
import java.util.*

class EventListRepositoryImpl(val retrofitClient: RetrofitClient) : EventListRepository {
    private val myEvents = ArrayList<Event>()
    private val likedEvents = ArrayList<Event>()

    override fun loadMyEvents(): Single<List<Event>> {

        return retrofitClient.api
            .loadMyEvents()
            .doOnSuccess {
                myEvents.clear()
                myEvents.addAll(it.data)
            }
            .map { myEvents }

    }

    override fun loadLikedEvents(): Single<List<Event>> {

        return retrofitClient.api
            .loadLikedEvents("like")
            .doOnSuccess {
                likedEvents.clear()
                likedEvents.addAll(it.data)
            }
            .map { likedEvents }

    }
}