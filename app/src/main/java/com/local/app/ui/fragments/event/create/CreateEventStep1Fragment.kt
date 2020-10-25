package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStep1Binding
import com.local.app.ui.BaseFragment

class CreateEventStep1Fragment : BaseCreateEventFragment<FragmentCreateEventStep1Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep1Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.etInputTitle.setText(viewModel.eventBuilder().title)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStep2Fragment()
    }

    override fun onValidate(): Boolean {
        return !binding.etInputTitle.text.isNullOrEmpty()
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_1)
    }

    override fun onNext() {
    }

}