package com.local.app.domain.profile.interactors

import com.local.app.data.Profile
import com.local.app.repository.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class GetProfileInteractor @Inject constructor(private val profileRepository: ProfileRepository) {

    fun getProfile(): Single<Profile> {
        return profileRepository.getProfile()
    }

    fun isProfileLoaded(): Boolean {
        return profileRepository.isProfileLoaded()
    }
}