package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStep1Binding
import com.local.app.ui.BaseFragment
import com.local.app.utils.SimpleTextWatcher

class CreateEventStep1Fragment : BaseCreateEventFragment<FragmentCreateEventStep1Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep1Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
    }

    override fun onResume() {
        super.onResume()
        log("onResume1 ${viewModel.eventBuilder().title}")
        binding.etInputTitle.setText(viewModel.eventBuilder().title)
        binding.etInputTitle.addTextChangedListener(textListener)
        log("onResume2 ${viewModel.eventBuilder().title}")
        binding.etInputTitle.setText(viewModel.eventBuilder().title)
    }

    override fun onPause() {
        super.onPause()
        binding.etInputTitle.removeTextChangedListener(textListener)
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

    private val textListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            log("onTextChanged $s")
            if (s.isNullOrEmpty()) return
            viewModel.eventBuilder().title = binding.etInputTitle.text.toString()
        }
    }

}