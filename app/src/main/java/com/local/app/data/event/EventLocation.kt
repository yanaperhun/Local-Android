package com.local.app.data.event

import android.os.Parcelable
import android.text.TextUtils
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventLocation(var id: Long,
                         var name: String,
                         var lat: Double,
                         var long: Double,
                         var distance: String = "Точное расстояние не определено") : Parcelable {

    val defaultDistance
        get() = if (TextUtils.isEmpty(distance)) "Точное расстояние не определено" else distance
}
