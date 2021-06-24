package com.local.app.presentation.viewmodel.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import com.local.app.R
import com.local.app.databinding.FragmentStepConnectToBinding
import com.local.app.ui.BaseFragment
import com.local.app.ui.fragments.event.create.BaseCreateEventFragment
import com.local.app.ui.fragments.event.create.CreateEventStepPhotoFragment
import com.local.app.utils.FieldValidation
import com.redmadrobot.inputmask.MaskedTextChangedListener

class StepConnectToFragment : BaseCreateEventFragment<FragmentStepConnectToBinding>() {
    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentStepConnectToBinding.inflate(inflater)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepPhotoFragment()
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        initPhoneMask()
        initWhatsappMask()

        val profile = viewModel.getProfile()!!
        if (binding.etPhone.text.isEmpty() && profile.phone.isNotEmpty()) {
            binding.etPhone.setText(profile.phone)
        }

        if (binding.etTelegram.text.isEmpty() && profile.telegram?.isNotEmpty() == true) {
            binding.etTelegram.setText(profile.telegram)
        }

        if (binding.etInstagram.text.isEmpty() && profile.instagram?.isNotEmpty() == true) {
            binding.etInstagram.setText(profile.instagram)
        }

        if (binding.etWhatsapp.text.isEmpty() && profile.whatsApp?.isNotEmpty() == true) {
            binding.etWhatsapp.setText(profile.whatsApp)
        }

        initClearButtons()
    }

    private fun initClearButtons() {
        binding.btnClearPhone.setOnClickListener { binding.etPhone.setText("") }
        binding.btnClearInst.setOnClickListener { binding.etInstagram.setText("") }
        binding.btnClearWhatapp.setOnClickListener { binding.etWhatsapp.setText("") }
        binding.btnClearTg.setOnClickListener { binding.etTelegram.setText("") }
    }

    private fun initPhoneMask() {
        val listener =
            MaskedTextChangedListener(
                "+7 ([000]) [000]-[00]-[00]", true, binding.etPhone, null,
                null
            )
        binding.etPhone.addTextChangedListener(listener)
        binding.etPhone.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etTelegram.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }

    }

    private fun initWhatsappMask() {
        val listener =
            MaskedTextChangedListener(
                "+7 ([000]) [000]-[00]-[00]", true, binding.etWhatsapp, null,
                null
            )
        binding.etWhatsapp.addTextChangedListener(listener)
        binding.etWhatsapp.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun onNext() {

        viewModel.eventBuilder().phone = binding.etPhone.text.toString()
        viewModel.eventBuilder().telegram = binding.etTelegram.text.toString()
        viewModel.eventBuilder().whatsapp = binding.etWhatsapp.text.toString()
        viewModel.eventBuilder().instagram = binding.etInstagram.text.toString()
        super.onNext()
    }

    override fun onValidate(): Boolean {
        var result = true
        val phone = binding.etPhone.text.toString()
        val telegram = binding.etTelegram.text.toString()
        val whatsapp = binding.etWhatsapp.text.toString()
        val instagram = binding.etInstagram.text.toString()

        if (phone.isEmpty() && telegram.isEmpty() && whatsapp.isEmpty() && instagram.isEmpty()) {
            binding.etPhone.showError(true, getString(R.string.error_validation_contacts_are_empty))
            return false
        }

        if (phone.isNotEmpty() && !FieldValidation.checkPhone(phone)) {
            Log.d("Local", "===>> phone is not valid")
            binding.etPhone.showError(true, getString(R.string.error_validation_phone))
            result = false
        }

        if (telegram.isNotEmpty()) {
            val regex =
                ".*[\\W](@(?=.{5,64}(?:\\s|\$))(?![_])(?!.*[_]{2})[a-zA-Z0-9_]+(?<![_.])).*".toRegex()
            result = telegram.matches(regex)

            Log.d("Local", "==> result telegram $result")
        }

        if (instagram.isNotEmpty()) {
            //(?:^|[^\w])(?:@)([A-Za-z0-9_](?:(?:[A-Za-z0-9_]|(?:\.(?!\.))){0,28}(?:[A-Za-z0-9_]))?)
        }

        if (whatsapp.isNotEmpty() && !FieldValidation.checkPhone(whatsapp)) {
            binding.etPhone.showError(true, getString(R.string.error_validation_whatup))
            return false
        }
        if (!result) showToast(getValidateMessage())
        return result
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_connect_to)
    }
}