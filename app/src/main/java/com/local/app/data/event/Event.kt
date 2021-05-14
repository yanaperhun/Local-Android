package com.local.app.data.event

import android.os.Parcelable
import android.util.Log
import com.local.app.api.response.EventType
import com.local.app.data.User
import com.local.app.data.photo.PhotoEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(var id: Long,
                 var eventName: String,
                 var eventDate: String,
                 var description: String,
                 var creator: User,
                 var cost: String,
                 var eventLocation: EventLocation,
                 var phone: String?,
                 var whatsapp: String?,
                 var instagram: String?,
                 val telegram: String?,
                 var soundCloud: String? = "",
                 var appleMusic: String? = "",
                 var ageLimit: String,
                 var pictures: List<PhotoEntity>,
                 var tags: List<String>? = emptyList(),
                 var status: EventStatus,
                 var type: EventType) : Parcelable {

    val tagsDefault: List<String>
        get() = listOf("18+", "Tag1", "Long Tag", "Tag3", "18+")

    fun getAnyPlaylist(): String {
        return if (appleMusic?.isNotEmpty() == true) appleMusic ?: "" else soundCloud ?: ""
    }

    fun getFirstPhoto(): PhotoEntity? {
        return if (!pictures.isNullOrEmpty()) pictures.first() else null
    }

    fun isConnectionTypeExist(): Boolean {
        return !instagram.isNullOrEmpty() || !telegram.isNullOrEmpty() || !phone.isNullOrEmpty() || !whatsapp.isNullOrEmpty()
    }

    fun getFormattedPrice(): String {
        return if (cost.isNullOrEmpty() || cost == "0"  || cost == "Бесплатно") {
            "Free"
        } else {
            cost
        }
    }

    fun printConnectionsType() {
        Log.d("Local", "===>>>EventsType : instagram $instagram, telegram $telegram, phone $phone, whatsapp $whatsapp")
    }
}