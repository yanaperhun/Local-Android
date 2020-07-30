package com.local.app

import android.app.Application
import com.local.app.di.ComponentManager

class LocalApp : Application() {

   public lateinit var daggerManager: ComponentManager
    override fun onCreate() {
        super.onCreate()
        daggerManager = ComponentManager(this)
    }


}