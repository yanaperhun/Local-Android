package com.local.app.data.event.create

import com.google.android.gms.maps.model.LatLng

data class EventAddress(val name: String,
                        val address: String,
                        val lat: Double = 0.0,
                        val lng: Double = 0.0,
                        val city: Int = 1) {

    companion object {

        fun build(address: String, lanLng: LatLng): EventAddress {
            return EventAddress(address, address, lanLng.latitude, lanLng.longitude, 1)
        }
    }
}