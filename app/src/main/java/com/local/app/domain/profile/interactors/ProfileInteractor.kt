package com.local.app.domain.profile.interactors

import com.local.app.data.Profile
import com.local.app.repository.ProfileRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProfileInteractor @Inject constructor(private val profileRepository: ProfileRepository) {

    fun getProfileAsync(): Single<Profile> {
        return profileRepository.getProfileAsync()
    }

    fun getProfile(): Profile? {
        return profileRepository.getProfile()
    }

    fun isProfileLoaded(): Boolean {
        return profileRepository.isProfileLoaded()
    }

    fun updateUserName(firstName: String, lastName: String): Single<Profile> {
        return profileRepository.updateUserName(firstName, lastName)
    }

    fun updateUserEmail(email: String): Single<Profile> {
        return profileRepository.updateUserEmail(email)
    }

    fun updateUserPassword(password: String): Completable {
        return profileRepository.updateUserPassword(password)
    }

    fun updateUserWhatsapp(whatsapp: String): Single<Profile> {
        return profileRepository.updateUserWhatsapp(whatsapp)
    }

    fun updateUserInstagram(instagram: String): Single<Profile> {
        return profileRepository.updateUserInstagram(instagram)
    }

    fun updateUserTelegram(telegram: String): Single<Profile> {
        return profileRepository.updateUserTelegram(telegram)
    }

    fun updateUserPhone(phone: String): Single<Profile> {
        return profileRepository.updateUserPhone(phone)

    }
    fun logout() {
        profileRepository.logout()
    }
}