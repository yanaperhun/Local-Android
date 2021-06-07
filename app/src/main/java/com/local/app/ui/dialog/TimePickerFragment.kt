package com.local.app.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class TimePickerFragment(private val listener: TimePickerDialog.OnTimeSetListener) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return TimePickerDialog(requireActivity(), listener, 18, 0, true)
    }
}