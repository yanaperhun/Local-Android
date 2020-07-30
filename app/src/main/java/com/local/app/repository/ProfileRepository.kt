package com.local.app.repository

import com.local.app.data.User
import io.reactivex.Single

interface ProfileRepository {

    fun isProfileLoaded() : Boolean
    fun getProfile() : Single<User>

}