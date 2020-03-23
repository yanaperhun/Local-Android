package com.local.app

import android.app.Application
import com.local.app.di.ComponentManager

class LocalApp : Application() {

   public var daggerComponentManager: ComponentManager = ComponentManager()
    override fun onCreate() {
        super.onCreate()
        daggerComponentManager = ComponentManager()
    }


}