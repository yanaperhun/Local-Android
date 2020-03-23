package com.local.app.api

import com.local.app.data.Event
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface API {

    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNTg0OTc1MDI4LCJleHAiOjE2MjA5NzUwMjh9.j6cbkX8a93vHoDMywlzLep51dcxTqPU9vNPZG7BgZis")
    @GET("api/feed")
    fun load(): Single<Event>
}