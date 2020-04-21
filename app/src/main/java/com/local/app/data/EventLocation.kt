package com.local.app.data

import android.text.TextUtils

data class EventLocation(var id: Long,
                         var name: String,
                         var lat: Double,
                         var long: Double,
                         var city: String,
                         var distance: String = "Не определено") {

    val defaultDistance
        get() = if (TextUtils.isEmpty(distance)) "Не определенно" else distance
}
