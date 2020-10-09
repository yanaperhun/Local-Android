package com.local.app.data.event.list

import com.local.app.data.event.Event
import io.reactivex.Single

interface EventListRepository {

    fun getLikedEvents() : Single<List<Event>>
    fun getMyEvents() : Single<List<Event>>
}