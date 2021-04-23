package com.local.app.data

import android.os.Parcelable
import com.local.app.data.photo.PhotoEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var id: Long,
                var firstName: String,
                var lastName: String,
                val pictures: List<PhotoEntity>? = null) : Parcelable {

    val fullName: String
        get() = "$firstName $lastName"

    fun getAnyUserPicture() : PhotoEntity? {
        return if (pictures?.isNotEmpty() == true) pictures.first() else null
    }
}