package com.local.app

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.local.app.di.ComponentManager
import java.util.*

class LocalApp : Application() {

    public lateinit var daggerManager: ComponentManager
    override fun onCreate() {
        super.onCreate()
        daggerManager = ComponentManager(this)
        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.api_key), Locale.getDefault())
        }
    }

}