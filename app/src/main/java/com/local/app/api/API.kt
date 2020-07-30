package com.local.app.api

import com.local.app.api.requests.SocNetAuthRequest
import com.local.app.api.response.EventsFeedResponse
import com.local.app.api.response.TokenResponse
import com.local.app.data.Profile
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @GET("api/feed")
    fun load(): Single<EventsFeedResponse>

    @POST("api/auth/tokensignin")
    fun auth(@Body socNetAuthRequest: SocNetAuthRequest): Single<TokenResponse>

    @GET("api/user")
    fun loadProfile(): Single<Profile>
}