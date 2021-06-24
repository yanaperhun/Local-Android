package com.local.app.data

import com.local.app.data.photo.PhotoEntity

data class Profile(
    var firstName: String?,
    var lastName: String?,
    var email: String?,
    var phone: String? = "",
    var age: Int,
    var pictures: List<PhotoEntity>?,
    var whatsApp: String?,
    var telegram: String?,
    var instagram: String?
) {


    fun getProfileImage(): PhotoEntity? {
        pictures?.let { if (it.isNotEmpty()) return it.first() }
        return null
    }

    fun getNick(): String {
        if (firstName.isNullOrEmpty() && lastName.isNullOrEmpty()) {
            return email ?: ""
        } else if (lastName.isNullOrEmpty()) {
            return "$firstName"
        } else if (firstName.isNullOrEmpty()) {
            return "${lastName}"
        } else {
            return "${firstName} ${lastName}"
        }
    }
}