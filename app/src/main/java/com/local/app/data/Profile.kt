package com.local.app.data

import com.local.app.data.photo.PhotoEntity

data class Profile(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val phone: String,
    val age: Int,
    val pictures: List<PhotoEntity>?,
    val whatsApp: String?,
    val telegram: String?,
    val instagram: String?
) {


    fun getProfileImage(): PhotoEntity? {
        pictures?.let { if (it.isNotEmpty()) return pictures.first() }
        return null
    }

    fun getUserName(): String {
        if (firstName.isNullOrEmpty() && lastName.isNullOrEmpty()) {
            return if (email.isNullOrEmpty()) "" else email
        } else if (lastName.isNullOrEmpty()) {
            return "$firstName"
        } else if (firstName.isNullOrEmpty()) {
            return "${lastName}"
        } else {
            return "${firstName} ${lastName}"
        }
    }
}