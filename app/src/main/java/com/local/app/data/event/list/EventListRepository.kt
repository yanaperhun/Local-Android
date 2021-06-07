package com.local.app.data.event.list

import com.local.app.data.event.Event
import io.reactivex.Single

interface EventListRepository {

    fun loadLikedEvents() : Single<List<Event>>
    fun loadMyEvents() : Single<List<Event>>
}