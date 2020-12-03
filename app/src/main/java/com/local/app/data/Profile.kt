package com.local.app.data

import com.local.app.data.photo.Photo

data class Profile(val firstName: String,
                   val lastName: String,
                   val email: String,
                   val phone: String,
                   val age: Int,
                   val pictures: List<Photo>?) {

    fun getProfileImage(): Photo? {
        pictures?.let { if (it.isNotEmpty()) return pictures.first() }
        return null
    }
}