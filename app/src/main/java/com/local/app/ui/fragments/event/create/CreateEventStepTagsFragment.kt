package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepTagsBinding
import com.local.app.ui.BaseFragment
import com.local.app.utils.SimpleTextWatcher

class CreateEventStepTagsFragment : BaseCreateEventFragment<FragmentCreateEventStepTagsBinding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepTagsBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)

    }

    override fun onResume() {
        super.onResume()
        binding.etInputTags.setText(viewModel.eventBuilder().description)
        var tagsConcat = ""
        viewModel.eventBuilder().tags.forEach { tagsConcat += "$it, " }
        binding.etInputTags.setText(tagsConcat)

    }

    override fun onPause() {
        super.onPause()
        binding.etInputTags.removeTextChangedListener(textListener)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepDateFragment()
    }

    override fun onValidate(): Boolean {
        return !binding.etInputTags.text.isNullOrEmpty()
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_2)
    }

    override fun onNext() {
        viewModel.eventBuilder().tags = binding.etInputTags.text
            .split(",")
            .map { it.trim() }
    }

    private val textListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) return
            //            viewModel.eventBuilder().description = binding.etInputDescription.text.toString()
        }
    }

}