package com.local.app.data.login

import com.local.app.api.RetrofitClient
import com.local.app.api.requests.SNAuthRequest
import com.local.app.api.response.TokenResponse
import com.retail.core.prefs.PrefUtils
import io.reactivex.Single

class LoginRepositoryImpl(private val retrofitClient: RetrofitClient,
                          private val prefUtils: PrefUtils) : LoginRepository {
    override fun loginBySocNetworks(snAuthRequest: SNAuthRequest): Single<TokenResponse> {
        return retrofitClient.api
            .auth(snAuthRequest)
            .doOnSuccess {
                prefUtils.saveTokens(it.token)
                retrofitClient.reInitClient()
            }
    }
}