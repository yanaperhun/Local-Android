package com.local.app.data.login

import com.local.app.api.requests.SocNetAuthRequest
import com.local.app.api.response.TokenResponse
import io.reactivex.Single

interface LoginRepository {

    fun loginBySocNetworks(socNetAuthRequest: SocNetAuthRequest): Single<TokenResponse>
}