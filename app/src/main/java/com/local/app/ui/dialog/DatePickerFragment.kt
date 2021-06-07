package com.local.app.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(private val listener: DatePickerDialog.OnDateSetListener) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(), listener, year, month, day).apply {
            datePicker.minDate = System.currentTimeMillis() + DateUtils.HOUR_IN_MILLIS
        }

    }
}