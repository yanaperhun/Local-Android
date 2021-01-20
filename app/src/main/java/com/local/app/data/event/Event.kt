package com.local.app.data.event

import com.local.app.api.response.EventType
import com.local.app.data.photo.PhotoEntity
import com.local.app.data.User

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
                 var pictures: List<PhotoEntity>,
                 var tags: List<String>,
                 var status: EventStatus,
                 var type: EventType) {

    val tagsDefault: List<String>
        get() = if (tags.isNullOrEmpty()) listOf("18+", "Tag1", "Long Tag", "Tag3") else tags
}