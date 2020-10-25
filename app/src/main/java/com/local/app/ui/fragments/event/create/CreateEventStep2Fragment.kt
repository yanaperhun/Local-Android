package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStep2Binding
import com.local.app.ui.BaseFragment

class CreateEventStep2Fragment : BaseCreateEventFragment<FragmentCreateEventStep2Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep2Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.etInputDescription.setText(viewModel.eventBuilder().title)

    }
    override fun getNextFragment(): BaseFragment {
        return CreateEventStep3Fragment()
    }

    override fun onValidate(): Boolean {
        return !binding.etInputDescription.text.isNullOrEmpty()
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_2)
    }

    override fun onNext() {
    }

}