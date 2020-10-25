package com.local.app.repository

import com.local.app.api.RetrofitClient
import com.local.app.data.Profile
import com.retail.core.prefs.PrefUtils
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private var client: RetrofitClient,
                                                private val prefUtils: PrefUtils) :
    ProfileRepository {

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

}