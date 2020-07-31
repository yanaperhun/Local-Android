package com.local.app.utils

import android.content.res.Resources

class Utils {
    companion object {
        val DP: Float = Resources.getSystem().displayMetrics.density
        val DP_INT: Int = DP.toInt()
    }
}