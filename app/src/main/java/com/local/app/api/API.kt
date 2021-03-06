package com.local.app.api

import com.local.app.api.requests.AuthRequest
import com.local.app.api.requests.LoginRequest
import com.local.app.api.requests.SocNetAuthRequest
import com.local.app.api.response.EventCreateResponse
import com.local.app.api.response.EventListResponse
import com.local.app.api.response.TokenResponse
import com.local.app.data.Profile
import com.local.app.data.event.create.EventRaw
import com.local.app.data.photo.PhotoEntity
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface API {

    @GET("api/feed/guest")
    fun load(): Single<EventListResponse>

    @POST("api/auth/tokensignin")
    fun authViaSocNetwork(@Body socNetAuthRequest: SocNetAuthRequest): Single<TokenResponse>

    @POST("api/auth/signin")
    fun authViaEmail(@Body authRequest: AuthRequest): Single<TokenResponse>

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Single<TokenResponse>

    @GET("api/user")
    fun loadProfile(): Single<Profile>

    @GET("api/events/touched")
    fun loadLikedEvents(@Query("type") type: String): Single<EventListResponse>

    @GET("api/events/my")
    fun loadMyEvents(): Single<EventListResponse>

    @POST("api/image-upload")
    @Multipart
    fun loadImage(@Part fileType :  MultipartBody.Part, @Part file:  MultipartBody.Part): Single<PhotoEntity>

    @POST("api/events")
    fun createEvent(@Body build: EventRaw): Single<EventCreateResponse>
}