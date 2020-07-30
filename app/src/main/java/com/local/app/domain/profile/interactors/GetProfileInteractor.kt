package com.local.app.domain.profile.interactors

import com.local.app.data.User
import com.local.app.repository.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class GetProfileInteractor @Inject constructor(private val profileRepository: ProfileRepository) {

    fun getProfile(): Single<User> {
        return profileRepository.getProfile()
    }

    fun isProfileLoaded(): Boolean {
        return profileRepository.isProfileLoaded()
    }
}