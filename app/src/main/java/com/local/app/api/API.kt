package com.local.app.api

import com.local.app.api.requests.AuthRequest
import com.local.app.api.requests.LoginRequest
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
    fun authViaSocNetwork(@Body socNetAuthRequest: SocNetAuthRequest): Single<TokenResponse>

    @POST("api/auth/signin")
    fun authViaEmail(@Body authRequest: AuthRequest): Single<TokenResponse>

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Single<TokenResponse>

    @GET("api/user")
    fun loadProfile(): Single<Profile>
}