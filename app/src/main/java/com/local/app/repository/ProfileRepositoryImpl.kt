package com.local.app.repository

import com.local.app.api.RetrofitClient
import com.local.app.data.Profile
import com.local.app.pref.PrefUtils
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private var client: RetrofitClient,
    private val prefUtils: PrefUtils
) : ProfileRepository {

    override fun isProfileLoaded(): Boolean {
        return prefUtils.getProfile() != null
    }

    override fun getProfileAsync(): Single<Profile> {
        val profileLocalMem = getProfile()
        profileLocalMem?.let {
            return@getProfileAsync Single.just(it)
        }
        return client.api
            .loadProfile()
            .map {
                saveProfile(it)
                return@map it
            }
    }

    override fun getProfile(): Profile? {
        return prefUtils.getProfile()
    }

    private fun saveProfile(profile: Profile) {
        prefUtils.saveProfile(profile)
    }

    override fun saveToken(token: String) {

    }

    override fun updateUserName(firstName: String, lastName: String): Single<Profile> {
        return client.api
            .updateProfile(mapOf(Pair("firstName", firstName), Pair("lastName", lastName)))
    }

    override fun updateUserEmail(email: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("email", email)))
    }

    override fun updateUserPassword(password: String): Completable {
        prefUtils.savePassword(password)
        return Completable.complete()
    }

    override fun updateUserWhatsapp(whatsapp: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("whatsapp", whatsapp)))
    }

    override fun updateUserInstagram(instagram: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("instagram", instagram)))
    }

    override fun updateUserTelegram(telegram: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("telegram", telegram)))
    }

    override fun updateUserPhone(phone: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("phone", phone)))
    }
}