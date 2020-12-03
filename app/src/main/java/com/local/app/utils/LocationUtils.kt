package com.local.app.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import java.util.*

class LocationUtils {

    companion object {

        val TAG = "LocationUtils"
        val EMPTY_LATLNG = LatLng(0.0, 0.0)
        fun defaultLocation(): LatLng {
            return LatLng(45.0462763, 38.9785348)
        }

        fun getPlaceByLatLng(latLng: LatLng, context: Context): Address {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            Log.d("LocationUtils", "${addresses.first()}")
            return addresses.first()
        }

        fun getPlaceFormattedByLatLng(latLng: LatLng, context: Context): String {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            Log.d("LocationUtils", "${addresses.first()}")
            val address = addresses.first()
            if (address.thoroughfare != null) {
                return "${address.thoroughfare ?: ""}, ${address.featureName}"
            } else {
                return address.featureName
            }

        }
    }

}