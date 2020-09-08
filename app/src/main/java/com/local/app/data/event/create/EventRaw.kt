package com.local.app.data.event.create

class EventRaw() {

    constructor(title: String?,
                description: String?,
                tags: List<String>?,
                date: Long,
                duration: Long,
                price: Double,
                address: EventAddress?,
                image: String?,
                playlist: List<String>?) : this()

    data class Builder(var title: String? = null,
                       var description: String? = null,
                       var tags: List<String>? = null,
                       var date: Long = 0,
                       var duration: Long = 0,
                       var price: Double = 0.0,
                       var address: EventAddress? = null,
                       var image: String? = null,
                       var playlist: List<String>? = null) {

        fun title(title: String) {
            this.title = title
        }

        fun description(description: String?) {
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

        fun price(price: Double) {
            this.price = price
        }

        fun address(eventAddress: EventAddress) {
            this.address = eventAddress
        }

        fun image(image: String?) {
            this.image = image
        }

        fun playList(playlist: List<String>?) {
            this.playlist = playlist
        }

        fun build(): EventRaw {
            return EventRaw(title, description, tags, date, duration, price, address, image,
                            playlist)
        }

    }

}