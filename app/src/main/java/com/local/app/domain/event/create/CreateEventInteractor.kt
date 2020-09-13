package com.local.app.domain.event.create

import com.local.app.repository.event.create.CreateEventRepository
import javax.inject.Inject

class CreateEventInteractor @Inject constructor(val createEventRepository: CreateEventRepository) {

}