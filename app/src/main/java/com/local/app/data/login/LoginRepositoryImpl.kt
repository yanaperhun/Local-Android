package com.local.app.data.login

import com.local.app.api.RetrofitClient
import com.local.app.api.requests.AuthRequest
import com.local.app.api.requests.LoginRequest
import com.local.app.api.requests.SocNetAuthRequest
import com.local.app.api.response.TokenResponse
import com.local.app.pref.PrefUtils
import io.reactivex.Single

class LoginRepositoryImpl(private val retrofitClient: RetrofitClient,
                          private val prefUtils: PrefUtils
) : LoginRepository {
    override fun loginBySocNetworks(socNetAuthRequest: SocNetAuthRequest): Single<TokenResponse> {
        return retrofitClient.api
            .authViaSocNetwork(socNetAuthRequest)
            .doOnSuccess {
                prefUtils.saveTokens(it.token)
                retrofitClient.reInitClient()
            }
    }

    override fun auth(name: String, email: String, pass: String): Single<TokenResponse> {
        return retrofitClient.api
            .authViaEmail(AuthRequest(name, email, pass))
            .doOnSuccess {
                prefUtils.saveTokens(it.token)
                retrofitClient.reInitClient()
            }
    }

    override fun login(email: String, pass: String): Single<TokenResponse> {
        val deviceName  = "1234"
        return retrofitClient.api
            .login(LoginRequest(email, pass, deviceName))
            .doOnSuccess {
                prefUtils.saveTokens(it.token)
                retrofitClient.reInitClient()
            }
    }
}