package com.local.app.api.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventType(var id: Int, var name: String) : Parcelable