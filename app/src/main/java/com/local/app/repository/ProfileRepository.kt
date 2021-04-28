package com.local.app.repository

import com.local.app.data.Profile
import io.reactivex.Single

interface ProfileRepository {

    fun isProfileLoaded() : Boolean
    fun getProfileAsync(): Single<Profile>
    fun getProfile() : Profile?
    fun logout()

    fun saveToken(token : String)

}