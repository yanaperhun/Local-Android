package com.local.app.data.event.create

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class EventRaw internal constructor(@SerializedName("eventName") val title: String = "",
                                    val description: String = "",
                                    val tags: List<String> = emptyList(),

                                    @SerializedName("eventDate") val date: Long = 0,
                                    val duration: Long = 0,

                                    @SerializedName("cost") val price: BigDecimal = BigDecimal.ZERO,

                                    @SerializedName(
                                        "eventLocation") val address: EventAddress? = null,
                                    val contactPhone: String = "",
                                    val instagram: String = "",
                                    val soundCloud: String = "",
                                    val appleMusic: String = "",
                                    val ageLimit: Int = 0,
                                    val pictures: List<String> = emptyList(),
                                    val playlist: List<String> = emptyList()) {

    class Builder() {

        var title: String = ""
        var description = ""
        private var tags: List<String> = emptyList()
        private var date: Long = 0
        private var duration: Long = 0
        private var price: BigDecimal = BigDecimal.ZERO
        private var address: EventAddress? = null
        private var contactPhone: String = ""
        private var instagram: String = ""
        private var soundCloud: String = ""
        private var appleMusic: String = ""
        private var ageLimit: Int = 0
        private var pictures: List<String> = emptyList()
        private var playlist: List<String> = emptyList()

        fun title(title: String) {
            this.title = title
        }

        fun description(description: String) {
            this.description = description
        }

        fun tags(tags: List<String>) {
            this.tags = tags
        }

        fun date(date: Long) {
            this.date = date
        }

        fun duration(duration: Long) {
            this.duration = duration
        }

        fun price(price: BigDecimal) {
            this.price = price
        }

        fun address(eventAddress: EventAddress) {
            this.address = eventAddress
        }

        fun contactPhone(phone: String) {
            this.contactPhone = phone
        }

        fun instagram(instagramLink: String) {
            this.instagram = instagramLink
        }

        fun soundCloud(soundCloudLink: String) {
            this.soundCloud = soundCloudLink
        }

        fun appleMusic(appleMusicLink: String) {
            this.appleMusic = appleMusicLink
        }

        fun ageLimit(ageLimit: Int) {
            this.ageLimit = ageLimit
        }

        fun pictures(pictures: List<String>) {
            this.pictures = pictures
        }

        fun playList(playlist: List<String>) {
            this.playlist = playlist
        }

        fun build(): EventRaw {
            return EventRaw(title, description, tags, date, duration, price, address, contactPhone,
                            instagram, soundCloud, appleMusic, ageLimit, pictures, playlist)
        }

    }
}
