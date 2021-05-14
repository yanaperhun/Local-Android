package com.local.app.data.event.create

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName
import com.local.app.data.photo.PhotoInDir

class EventRaw internal constructor(@SerializedName("eventName") val title: String = "",
                                    val description: String = "",
                                    val tags: List<String> = emptyList(),
                                    @SerializedName("eventDate") val date: Long = 0,
                                    val time: Long = 0L,
                                    @SerializedName("cost") val price: String = "Free",
                                    @SerializedName(
                                        "eventLocation") val address: EventAddress? = null,
                                    var phone: String = "",
                                    var whatsapp: String?,
                                    var instagram: String?,
                                    val telegram: String?,
                                    val soundCloud: String = "",
                                    val appleMusic: String = "",
                                    val ageLimit: Int = 0,
                                    val pictures: List<String> = emptyList(),
                                    val playlist: List<String> = emptyList()) {

    class Builder : BaseObservable() {

        var title: String = ""
        var description = ""
        var tags: List<String> = emptyList()
        var date: Long = 0
        var time: Long = 0L
        var price: String = "Free"
        var eventAddress: EventAddress? = null
        var phone: String = ""
        var whatsapp: String = ""
        var instagram: String = ""
        var telegram: String = ""
        private var soundCloud: String = ""
        private var appleMusic: String = ""
        private var ageLimit: Int = 0
        var pictures: MutableList<PhotoInDir> = ArrayList()
        private var playlist: MutableList<String> = ArrayList()

        fun title(title: String) {
            this.title = title
        }

        fun getSinglePlayList(): String {
            return if (playlist.isNotEmpty()) playlist.first() else ""
        }

        fun setSinglePlayList(playlistValue: String) {
            playlist.clear()
            playlist.add(playlistValue)
        }

        fun build(): EventRaw {
            return EventRaw(title, description, tags, date, time, price, eventAddress, phone,
                            whatsapp, instagram, telegram, soundCloud, appleMusic, ageLimit,
                            pictures.map { it.hash }, playlist)
        }

    }
}
