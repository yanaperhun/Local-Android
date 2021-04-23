package com.local.app.data.photo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoEntity(var id: Long,
                       var hash: String,
                       val url: PhotoUrl): Parcelable {


}