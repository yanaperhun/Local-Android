package com.local.app.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.local.app.R


class AutoCompleteEditTextE @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AutoCompleteTextView(context, attrs, defStyleAttr) {

    fun showError(isError: Boolean, message: String = "") {
        if (isError) {
            setBackgroundDrawable(resources.getDrawable(R.drawable.error_bg))
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            setBackgroundDrawable(resources.getDrawable(R.drawable.rect_grey_solid_light_stroke__grey_12dp_corner))
        }

    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        showError(false)
    }

}