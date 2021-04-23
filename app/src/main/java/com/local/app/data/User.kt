package com.local.app.data

import com.local.app.data.photo.PhotoEntity

data class User(var id: Long,
                var firstName: String,
                var lastName: String,
                val pictures: List<PhotoEntity>? = null) {

    val fullName: String
        get() = "$firstName $lastName"

    fun getAnyUserPicture() : PhotoEntity? {
        return if (pictures?.isNotEmpty() == true) pictures.first() else null
    }
}