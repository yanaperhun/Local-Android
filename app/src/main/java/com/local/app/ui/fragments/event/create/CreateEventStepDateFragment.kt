package com.local.app.ui.fragments.event.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepDateBinding
import com.local.app.ui.BaseFragment
import com.local.app.ui.dialog.DatePickerFragment
import com.local.app.ui.dialog.TimePickerFragment
import com.local.app.utils.DateUtils
import com.local.app.utils.SimpleTextWatcher

class CreateEventStepDateFragment : BaseCreateEventFragment<FragmentCreateEventStepDateBinding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepDateBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.etInputDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.tvTime.setOnClickListener {
            showTimePickerDialog()
        }
        if (viewModel.eventBuilder().date != 0L) {
            setFormattedDate()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etInputDate.addTextChangedListener(dateWatcher)
        setFormattedTime()
        setFormattedDate()
    }

    override fun onPause() {
        super.onPause()
        binding.etInputDate.removeTextChangedListener(dateWatcher)
    }

    private fun setFormattedDate() {
        binding.etInputDate.text = DateUtils.formatLongToString(viewModel.eventBuilder().date,
                                                                DateUtils.FORMAT_dd_MMMM_yyyy)
    }

    private fun setFormattedTime() {
        log("time : ${viewModel.eventBuilder().time}")
        binding.tvTime.text =
            DateUtils.formatLongToString(viewModel.eventBuilder().time, DateUtils.FORMAT_HH_mm)
    }

    private fun showDatePickerDialog() {
        val datePicker =
            DatePickerFragment(DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.eventBuilder().date = DateUtils.getMillis(year, month, dayOfMonth)
                setFormattedDate()
            })
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun showTimePickerDialog() {

        val datePicker = TimePickerFragment(TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            log("date millis ${viewModel.eventBuilder().date}")
            log("hour $hour, mins $minute")
            viewModel.eventBuilder().time =
                viewModel.eventBuilder().date + DateUtils.getMillis(hour, minute)
            setFormattedTime()
        })
        datePicker.show(parentFragmentManager, "timePicker")
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepPlaceFragment()
    }

    override fun onNext() {
    }

    private val dateWatcher = object : SimpleTextWatcher() {

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            super.onTextChanged(s, start, before, count)
            binding.tvTime.isEnabled = !binding.etInputDate.text.isNullOrEmpty()
        }
    }

    override fun onValidate(): Boolean {
        return !binding.etInputDate.text.isNullOrEmpty() && !binding.tvTime.text.isNullOrEmpty()
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_3)
    }
}