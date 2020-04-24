package com.local.app.ui

import android.content.res.Resources
import android.util.TypedValue

class Utils {
    companion object {
        val DP: Float = Resources.getSystem().displayMetrics.density
        val DP_INT: Int = DP.toInt()
    }
}