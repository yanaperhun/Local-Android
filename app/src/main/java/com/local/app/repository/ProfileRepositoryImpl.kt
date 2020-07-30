package com.local.app.repository

import com.local.app.api.RetrofitClient
import com.local.app.data.User
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private var client: RetrofitClient) :
    ProfileRepository {

    override fun isProfileLoaded(): Boolean {
        return true
    }

    override fun getProfile(): Single<User> {
        return Single.just(User(1L, "Qa", "Ti"))
    }

}