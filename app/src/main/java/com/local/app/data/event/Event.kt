package com.local.app.data.event

import com.local.app.api.response.EventType
import com.local.app.data.User
import com.local.app.data.photo.PhotoEntity

data class Event(var id: Long,
                 var eventName: String,
                 var eventDate: String,
                 var description: String,
                 var creator: User,
                 var cost: String,
                 var eventLocation: EventLocation,
                 var contactPhone: String?,
                 var instagram: String?,
                 var soundCloud: String? = "",
                 var appleMusic: String? = "",
                 var ageLimit: String,
                 var pictures: List<PhotoEntity>,
                 var tags: List<String>? = emptyList(),
                 var status: EventStatus,
                 var type: EventType) {

    val tagsDefault: List<String>
        get() = listOf("18+", "Tag1", "Long Tag", "Tag3", "18+")

    fun getAnyPlaylist(): String {
        return if (appleMusic?.isNotEmpty() == true) appleMusic ?: "" else soundCloud ?: ""
    }

    fun getFormattedPrice(): String {
        return if (cost.isNullOrEmpty() || cost == "0") {
            "Free"
        } else {
            cost
        }
    }
}