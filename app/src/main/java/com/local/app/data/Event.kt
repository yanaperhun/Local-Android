package com.local.app.data

import com.local.app.api.response.EventType

data class Event(var id: Long,
                 var eventName: String,
                 var eventDate: String,
                 var description: String,
                 var creator: User,
                 var cost: Int,
                 var eventLocation: EventLocation,
                 var contactPhone: String?,
                 var instagram: String?,
                 var soundCloud: String?,
                 var appleMusic: String?,
                 var ageLimit: Int,
                 var pictures: List<Photo>,
                 var status: EventStatus,
                 var type: EventType)