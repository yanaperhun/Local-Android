package com.local.app.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class TimePickerFragment(private val listener: TimePickerDialog.OnTimeSetListener) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //        val c = Calendar.getInstance()
        //        val year = c.get(Calendar.YEAR)
        //        val month = c.get(Calendar.MONTH)
        //        val day = c.get(Calendar.DAY_OF_MONTH)
        return TimePickerDialog(requireActivity(), listener, 18, 0, true)

    }
}