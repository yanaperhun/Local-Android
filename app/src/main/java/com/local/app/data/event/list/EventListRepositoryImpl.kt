package com.local.app.data.event.list

import com.local.app.api.RetrofitClient
import com.local.app.data.event.Event
import io.reactivex.Single
import java.util.*

class EventListRepositoryImpl(val retrofitClient: RetrofitClient) : EventListRepository {
    private val myEvents = ArrayList<Event>()
    private val likedEvents = ArrayList<Event>()

    override fun getMyEvents(): Single<List<Event>> {
        return if (myEvents.isNullOrEmpty()) {
            retrofitClient.api
                .loadMyEvents()
                .doOnSuccess { myEvents.addAll(it.data) }
                .map { myEvents }
        } else {
            Single.just(myEvents)
        }
    }

    override fun getLikedEvents(): Single<List<Event>> {
        return if (likedEvents.isNullOrEmpty()) {
            retrofitClient.api
                .loadLikedEvents()
                .doOnSuccess { likedEvents.addAll(it.data) }
                .map { likedEvents }
        } else {
            Single.just(likedEvents)
        }
    }
}