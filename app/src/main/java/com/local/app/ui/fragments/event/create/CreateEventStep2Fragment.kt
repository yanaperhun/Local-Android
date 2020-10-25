package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStep2Binding
import com.local.app.ui.BaseFragment
import com.local.app.utils.SimpleTextWatcher

class CreateEventStep2Fragment : BaseCreateEventFragment<FragmentCreateEventStep2Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep2Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()

    }

    override fun onResume() {
        super.onResume()
        binding.etInputDescription.setText(viewModel.eventBuilder().description)
        binding.etInputDescription.addTextChangedListener(textListener)
    }

    override fun onPause() {
        super.onPause()
        binding.etInputDescription.removeTextChangedListener(textListener)
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

    private val textListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) return
            viewModel.eventBuilder().description = binding.etInputDescription.text.toString()
        }
    }


}