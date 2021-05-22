package com.local.app.repository

import com.local.app.data.Profile
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileRepository {

    fun isProfileLoaded() : Boolean
    fun getProfileAsync(): Single<Profile>
    fun getProfile() : Profile?
    fun logout()

    fun saveToken(token : String)

    fun updateUserName(firstName: String, lastName: String): Single<Profile>
    fun updateUserEmail(email: String): Single<Profile>
    fun updateUserPassword(password: String): Completable
    fun updateUserWhatsapp(whatsapp: String): Single<Profile>
    fun updateUserInstagram(instagram: String): Single<Profile>
    fun updateUserTelegram(telegram: String): Single<Profile>
    fun updateUserPhone(phone: String): Single<Profile>
}