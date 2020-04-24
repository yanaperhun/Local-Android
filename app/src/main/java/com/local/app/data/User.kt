package com.local.app.data

data class User(var id: Long, var firstName: String, var lastName: String) {

    val fullName: String
        get() = "$firstName $lastName"
}