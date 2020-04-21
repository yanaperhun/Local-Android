package com.local.app.ui

import android.content.res.Resources
import android.util.TypedValue

class Utils {
    companion object {
        fun dpToPx(dp: Int, res: Resources): Int {
            return TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), res.getDisplayMetrics())
                .toInt()
        }
    }
}