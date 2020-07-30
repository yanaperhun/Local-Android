package com.local.app.data.login

import com.local.app.api.RetrofitClient
import com.local.app.api.requests.SNAuthRequest
import com.local.app.api.response.TokenResponse
import io.reactivex.Single

class LoginRepositoryImpl(private val retrofitClient: RetrofitClient) : LoginRepository {
    override fun socNetworksLogin(snAuthRequest: SNAuthRequest): Single<TokenResponse> {
        return retrofitClient.api.auth(snAuthRequest)
    }
}