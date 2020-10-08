package com.local.app.domain.event.list

import com.local.app.data.event.list.EventListRepository
import javax.inject.Inject

class LoadEventListInteractor @Inject constructor(private val eventListRepository: EventListRepository) {}