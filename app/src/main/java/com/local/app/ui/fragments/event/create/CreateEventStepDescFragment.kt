package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepDecsBinding
import com.local.app.ui.BaseFragment
import com.local.app.utils.SimpleTextWatcher

class CreateEventStepDescFragment : BaseCreateEventFragment<FragmentCreateEventStepDecsBinding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepDecsBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)

    }

    override fun onResume() {
        super.onResume()
        binding.etInputDescription.setText(viewModel.eventBuilder().description)
        binding.etInputDescription.addTextChangedListener(textListener)
        focusET(binding.etInputDescription)
    }

    override fun onPause() {
        super.onPause()
        binding.etInputDescription.removeTextChangedListener(textListener)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepTagsFragment()
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