package com.local.app.api

import com.local.app.api.requests.SNAuthRequest
import com.local.app.api.response.EventsFeedResponse
import com.local.app.api.response.TokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    @GET("api/feed")
    fun load(): Single<EventsFeedResponse>

    @POST("api/auth/tokensignin")
    fun auth(@Body snAuthRequest: SNAuthRequest): Single<TokenResponse>
}