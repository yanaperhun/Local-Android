package com.local.app.data

import com.local.app.data.photo.PhotoEntity

data class Profile(val firstName: String,
                   val lastName: String,
                   val email: String,
                   val phone: String,
                   val age: Int,
                   val pictures: List<PhotoEntity>?) {

    fun getProfileImage(): PhotoEntity? {
        pictures?.let { if (it.isNotEmpty()) return pictures.first() }
        return null
    }
}