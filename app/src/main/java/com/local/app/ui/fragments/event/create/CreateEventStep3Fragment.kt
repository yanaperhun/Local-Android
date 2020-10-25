package com.local.app.ui.fragments.event.create

import android.app.DatePickerDialog
import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStep3Binding
import com.local.app.ui.BaseFragment
import com.local.app.ui.dialog.DatePickerFragment
import com.local.app.utils.DateUtils

class CreateEventStep3Fragment : BaseCreateEventFragment<FragmentCreateEventStep3Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep3Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.etInputDate.setOnClickListener {
            showDatePickerDialog()
        }
        if (viewModel.eventBuilder().date != 0L) {
            setFormattedDate()
        }

        binding.tvDuration.setText(viewModel.eventBuilder().duration)
    }

    private fun setFormattedDate() {
        binding.etInputDate.text = DateUtils.formatLongToString(viewModel.eventBuilder().date,
                                                                DateUtils.FORMAT_dd_MMMM_yyyy)
    }

    private fun showDatePickerDialog() {
        val datePicker =
            DatePickerFragment(DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.eventBuilder().date = DateUtils.getMillis(year, month, dayOfMonth)
                setFormattedDate()
            })
        datePicker.show(parentFragmentManager, "datePicker")
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStep4Fragment()
    }

    override fun onNext() {
    }

    override fun onValidate(): Boolean {
        return !binding.etInputDate.text.isNullOrEmpty() && !binding.tvDuration.text.isNullOrEmpty()
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_3)
    }

}