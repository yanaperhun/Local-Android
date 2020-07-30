package com.local.app.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.local.app.api.interceptors.HeaderInterceptor
import com.retail.core.prefs.PrefUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(val prefUtils: PrefUtils) {

    private val TIMEOUT = 60L
    private val URL = "http://95.214.9.249/"

    lateinit var api: API

    init {
        initClient()
    }

    private fun initClient() {
        val gson: Gson?
        gson = try {
            GsonBuilder().create()
        } catch (e: Exception) {
            e.printStackTrace()
            Gson()
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(HeaderInterceptor(prefUtils.getToken()))
//            .addInterceptor(ErrorInterceptor())
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        val retrofit: Retrofit = Retrofit
            .Builder()
            .client(client)
            .baseUrl(URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(API::class.java)
    }

    fun reInitClient() {
        initClient()
    }

}