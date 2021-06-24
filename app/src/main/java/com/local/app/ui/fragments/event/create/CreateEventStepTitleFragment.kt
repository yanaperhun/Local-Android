package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepTitleBinding
import com.local.app.ui.BaseFragment
import com.local.app.utils.SimpleTextWatcher

class CreateEventStepTitleFragment : BaseCreateEventFragment<FragmentCreateEventStepTitleBinding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepTitleBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
    }

    override fun onResume() {
        super.onResume()
        log("onResume1 ${viewModel.eventBuilder().title}")
        binding.etInputTitle.setText(viewModel.eventBuilder().title)
        binding.etInputTitle.addTextChangedListener(textListener)
        log("onResume2 ${viewModel.eventBuilder().title}")
        binding.etInputTitle.setText(viewModel.eventBuilder().title)
        focusET(binding.etInputTitle)
    }

    override fun onPause() {
        super.onPause()
        binding.etInputTitle.removeTextChangedListener(textListener)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepDescFragment()
    }

    override fun onValidate(): Boolean {
        val isValidate =  !binding.etInputTitle.text.isNullOrEmpty()
        binding.etInputTitle.showError(!isValidate, getValidateMessage())
        return isValidate
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_1)
    }

    override fun onNext() {
        super.onNext()
    }

    private val textListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            log("onTextChanged $s")
            if (s.isNullOrEmpty()) return
            viewModel.eventBuilder().title = binding.etInputTitle.text.toString()
        }
    }

}