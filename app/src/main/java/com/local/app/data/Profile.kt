package com.local.app.data

import com.local.app.data.photo.PhotoEntity

data class Profile(
    val firstName: String,
    val lastName: String,
    val email: String,
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
        if (firstName == null && lastName == null) {
            return ""
        } else if (lastName == null) {
            return "$firstName"
        } else if (firstName == null) {
            return "${lastName}"
        } else {
            return "${firstName} ${lastName}"
        }
    }
}