package com.local.app.data.login

import com.local.app.api.requests.SNAuthRequest
import com.local.app.api.response.TokenResponse
import io.reactivex.Single

interface LoginRepository {

    fun socNetworksLogin(snAuthRequest: SNAuthRequest): Single<TokenResponse>
}