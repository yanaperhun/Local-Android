package com.retail.core.prefs

import android.content.Context
import com.google.gson.Gson
import com.local.app.data.Profile

class PrefUtils(val context: Context) {

    companion object {
        const val LOCAL_PREFS = "local_prefs"
        const val WELCOME_WAS_SHOWED = "welcome_was_showed"
        const val TOKEN = "tokens"
        const val PROFILE = "profile"
    }

    fun setWelcomeWasShowed(wasShowed: Boolean, context: Context) {
        val sharedPref = context.getSharedPreferences(LOCAL_PREFS, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(WELCOME_WAS_SHOWED, wasShowed)
            commit()
        }
    }

    fun getWelcomeWasShowed(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(LOCAL_PREFS, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(WELCOME_WAS_SHOWED, false)
    }

    fun saveTokens(token: String) {
        val sharedPref = context.getSharedPreferences(LOCAL_PREFS, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(TOKEN, token)
            commit()
        }
    }

    fun getToken(): String {
        val sharedPref = context.getSharedPreferences(LOCAL_PREFS, Context.MODE_PRIVATE)
        return sharedPref.getString(TOKEN, "") ?: ""

    }

    fun clearProfile() {
        val sharedPref = context.getSharedPreferences(LOCAL_PREFS, Context.MODE_PRIVATE)
        sharedPref.edit().remove(PROFILE).commit()
    }

    fun saveProfile(profile: Profile) {
        val sharedPref = context.getSharedPreferences(LOCAL_PREFS, Context.MODE_PRIVATE)
        val profileString = Gson().toJson(profile, Profile::class.java)
        with(sharedPref.edit()) {
            putString(PROFILE, profileString)
            commit()
        }
    }

    fun getProfile(): Profile? {
        val sharedPref = context.getSharedPreferences(LOCAL_PREFS, Context.MODE_PRIVATE)
        sharedPref
            .getString(PROFILE, "")
            ?.let {
                return Gson().fromJson(it, Profile::class.java)
            } ?: return null

    }
}