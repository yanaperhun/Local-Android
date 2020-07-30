package com.local.app.repository

import com.local.app.api.RetrofitClient
import com.local.app.data.Profile
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private var client: RetrofitClient) :
    ProfileRepository {

    override fun isProfileLoaded(): Boolean {
        return true
    }

    override fun getProfile(): Single<Profile> {
        return client.api.loadProfile()
    }

    override fun saveToken(token: String) {

    }

}