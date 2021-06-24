package com.local.app.ui.fragments.profile.settings

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.local.app.BindableFragment
import com.local.app.R
import com.local.app.data.Profile
import com.local.app.databinding.FragmentProfileSettingsBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import timber.log.Timber

class ProfileSettingsFragment : BindableFragment<FragmentProfileSettingsBinding>() {

    val viewModel: ProfileSettingsViewModel by viewModels()

    override fun subscribeToViewModel() {
        viewModel.personalDataInfo.observe(viewLifecycleOwner, Observer(::showProfileInfo))
        viewModel.state.observe(viewLifecycleOwner, Observer(::updateProfileSettingsState))
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.btnBack.setOnClickListener {
            Timber.d("click back button")
            backStep()
        }
        binding.btnClose.setOnClickListener { requireActivity().finish() }

        initOnEditorActionListener()
        subscribeToViewModel()
        viewModel.loadProfileInfo()

        val listener =
            MaskedTextChangedListener(
                "+7 ([000]) [000]-[00]-[00]", true, binding.etPhoneNumber, null,
                null
            )
        binding.etPhoneNumber.addTextChangedListener(listener)

        val listener2 =
            MaskedTextChangedListener(
                "+7 ([000]) [000]-[00]-[00]", true, binding.etWhatsApp, null,
                null
            )
        binding.etWhatsApp.addTextChangedListener(listener2)
    }

    private fun initOnEditorActionListener() {
        binding.etUserName.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserName(binding.etUserName.text.toString(), "")
                binding.etUserName.clearFocus()
                focusET(binding.etUserEmail)
                true
            } else {
                false
            }
        }
        binding.etUserEmail.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserEmail(binding.etUserEmail.text.toString())
                binding.etUserEmail.clearFocus()
                focusET(binding.etPhoneNumber)
                true
            } else {
                false
            }
        }
        binding.etPhoneNumber.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserPhone(binding.etPhoneNumber.text.toString())
                binding.etPhoneNumber.clearFocus()
                focusET(binding.etTelegram)
                true
            } else {
                false
            }
        }
        binding.etWhatsApp.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserWhatsApp(binding.etWhatsApp.text.toString())
                hideKeyboard()
                true
            } else {
                false
            }
        }
        binding.etTelegram.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserTelegram(binding.etTelegram.text.toString())
                binding.etTelegram.clearFocus()
                focusET(binding.etInstagram)
                true
            } else {
                false
            }
        }
        binding.etInstagram.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserInstagram(binding.etInstagram.text.toString())
                focusET(binding.etWhatsApp)
                true
            } else {
                false
            }
        }
//        binding.etPassword.setOnEditorActionListener { _, actionId, keyEvent ->
//            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
//                viewModel.updateUserPassword(binding.etPassword.text.toString())
//                binding.etPassword.clearFocus()
//                hideKeyboard()
//                true
//            } else {
//                false
//            }
//        }
    }


    private fun showProfileInfo(profile: Profile) {
        Timber.d("showProfileInfo $profile")
        binding.progressProfileSettings.visibility = View.GONE
        binding.nestedScrollProfileSettings.visibility = View.VISIBLE
        binding.etUserName.setText(profile.firstName ?: "")
        binding.etUserEmail.setText(profile.email)
        binding.etPhoneNumber.setText(if (profile.phone.isEmpty()) "" else profile.phone)
        binding.etWhatsApp.setText(
            if (profile.whatsApp?.isEmpty() ?: true) "" else profile.whatsApp
        )
        binding.etTelegram.setText(profile.telegram)
        binding.etInstagram.setText(profile.instagram)
    }

    private fun updateProfileSettingsState(state: ProfileSettingsState) {
        when (state) {
            is ProfileSettingsState.ERROR -> {
                showErrorAlert(state.error.message ?: getString(R.string.alert_title_error))
            }
            is ProfileSettingsState.SUCCESS -> {
                binding.progressProfileSettings.visibility = View.GONE
                binding.nestedScrollProfileSettings.visibility = View.VISIBLE
            }
            else -> {
                binding.progressProfileSettings.visibility = View.VISIBLE
                binding.nestedScrollProfileSettings.visibility = View.INVISIBLE
            }
        }
    }

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentProfileSettingsBinding.inflate(inflater)
    }
}