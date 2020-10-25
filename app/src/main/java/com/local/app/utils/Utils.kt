package com.local.app.utils

import android.content.res.Resources

class Utils {
    companion object {
        fun pxToDp(px: Float): Float {
            return px / Resources.getSystem().displayMetrics.density
        }

        fun dpToPx(dp: Int): Float {
            return dp * Resources.getSystem().displayMetrics.density
        }
    }


}