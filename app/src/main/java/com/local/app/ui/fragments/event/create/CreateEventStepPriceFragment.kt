package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepPriceBinding
import com.local.app.ui.BaseFragment
import com.local.app.utils.SimpleTextWatcher

class CreateEventStepPriceFragment :
    BaseCreateEventFragment<FragmentCreateEventStepPriceBinding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepPriceBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.etInputPrice.setText(viewModel.eventBuilder().price)

    }

    override fun onResume() {
        super.onResume()
        binding.etInputPrice.addTextChangedListener(priceWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.etInputPrice.removeTextChangedListener(priceWatcher)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepPlaceFragment()
    }

    override fun onNext() {
    }

    override fun onValidate(): Boolean {
        return !binding.etInputPrice.text.isNullOrEmpty()
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_price)
    }

    private val priceWatcher = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            super.onTextChanged(s, start, before, count)
            if (s.isNullOrEmpty()) return
            viewModel.eventBuilder().price = binding.etInputPrice.text.toString()
        }
    }
}