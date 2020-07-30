package com.retail.core.prefs

import android.content.Context

class PrefUtils(val context: Context) {

    val LOCAL_PREFS = "local_prefs"
    val WELCOME_WAS_SHOWED = "welcome_was_showed"
    val TOKEN = "tokens"

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

}