package com.local.app.repository

import com.local.app.api.RetrofitClient
import com.local.app.data.Profile
import com.local.app.pref.PrefUtils
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private var client: RetrofitClient,
    private val prefUtils: PrefUtils
) :
    ProfileRepository {

    private var profile: Profile? = null

    init {

        profile = getProfile()
        Timber.d("Init with profile $profile")
    }

    override fun isProfileLoaded(): Boolean {
        return prefUtils.getProfile() != null
    }

    override fun logout() {
        prefUtils.clearProfile()
    }

    override fun loadProfileAndSaveInPref(): Single<Profile> {
        return client.api
            .loadProfile()
            .map {
                saveProfile(it)
                return@map it
            }
    }

    override fun getProfile(): Profile? {
        Timber.d("getProfile : $profile")
        return profile
    }

    private fun saveProfile(_profile: Profile) {
        Timber.d("saveProfile : $_profile")
        profile = _profile
        profile?.let { prefUtils.saveProfile(it) }
    }

    override fun saveToken(token: String) {

    }

    override fun updateUserName(firstName: String, lastName: String): Single<Profile> {
        return client.api
            .updateProfile(mapOf(Pair("firstName", firstName), Pair("lastName", lastName)))
            .doOnComplete {
                Timber.d("profile : $profile")
                profile?.let { value ->
                    value.firstName = firstName
                    value.lastName = lastName
                    saveProfile(value)
                }

            }
            .toSingle { profile }

    }

    override fun updateUserEmail(email: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("email", email)))
            .doOnComplete {
                profile?.let { valur ->
                    Timber.d("profile : $profile")
                    valur.email = email
                    saveProfile(valur)
                }
            }
            .toSingle { profile }
    }

    override fun updateUserPassword(password: String): Completable {
        prefUtils.savePassword(password)
        return Completable.complete()
    }

    override fun updateUserWhatsapp(whatsapp: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("whatsapp", whatsapp)))
            .doOnComplete {
                profile?.let { value ->
                    value.whatsApp = whatsapp
                    saveProfile(value)
                }
            }
            .toSingle { profile }
    }


    override fun updateUserInstagram(instagram: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("instagram", instagram)))
            .doOnComplete {
                profile?.let { value ->
                    value.instagram = instagram
                    saveProfile(value)
                }
            }
            .toSingle { profile }
    }

    override fun updateUserTelegram(telegram: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("telegram", telegram)))
            .doOnComplete {
                profile?.let { value ->
                    value.telegram = telegram
                    saveProfile(value)
                }
            }
            .toSingle { profile }
    }

    override fun updateUserPhone(phone: String): Single<Profile> {
        return client.api.updateProfile(mapOf(Pair("phone", phone)))
            .doOnComplete {
                profile?.let { value ->
                    value.phone = phone
                    saveProfile(value)
                }
            }
            .toSingle { profile }
    }

    override fun updateUserPhoto(photoHash: String): Single<Profile> {
        return client.api.updatePhoto(photoHash)
            .doOnSuccess {
                profile?.let { value ->
                    value.pictures = it.pictures
                    saveProfile(value)
                }
            }
    }

}