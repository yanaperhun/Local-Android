package com.local.app.repository

import com.local.app.data.Profile
import io.reactivex.Single

interface ProfileRepository {

    fun isProfileLoaded() : Boolean
    fun getProfile(): Single<Profile>

    fun saveToken(token : String)

}