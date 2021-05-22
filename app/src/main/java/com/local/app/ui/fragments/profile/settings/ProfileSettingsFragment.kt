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

class ProfileSettingsFragment : BindableFragment<FragmentProfileSettingsBinding>() {

    val viewModel: ProfileSettingsViewModel by viewModels()

    override fun subscribeToViewModel() {
        viewModel.personalDataInfo.observe(viewLifecycleOwner, Observer(::showProfileInfo))
        viewModel.state.observe(viewLifecycleOwner, Observer(::updateProfileSettingsState))
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.btnClose.setOnClickListener { requireActivity().finish() }

        initOnEditorActionListener()
        subscribeToViewModel()
        viewModel.loadProfileInfo()
    }

    private fun initOnEditorActionListener() {
        binding.etUserName.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserName(binding.etUserName.text.toString(), "")
                binding.etUserName.clearFocus()
                hideKeyboard()
                true
            } else {
                false
            }
        }
        binding.etUserEmail.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserEmail(binding.etUserEmail.text.toString())
                binding.etUserEmail.clearFocus()
                hideKeyboard()
                true
            } else {
                false
            }
        }
        binding.etPhoneNumber.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserPhone(binding.etPhoneNumber.text.toString())
                binding.etPhoneNumber.clearFocus()
                hideKeyboard()
                true
            } else {
                false
            }
        }
        binding.etWhatsApp.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserWhatsApp(binding.etWhatsApp.text.toString())
                binding.etWhatsApp.clearFocus()
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
                hideKeyboard()
                true
            } else {
                false
            }
        }
        binding.etInstagram.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN) {
                viewModel.updateUserInstagram(binding.etInstagram.text.toString())
                binding.etInstagram.clearFocus()
                hideKeyboard()
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
        binding.progressProfileSettings.visibility = View.GONE
        binding.nestedScrollProfileSettings.visibility = View.VISIBLE
        binding.etUserName.setText(profile.getUserName())
        binding.etUserEmail.setText(profile.email ?: getString(R.string.email))
        binding.etPhoneNumber.setText(profile.phone ?: getString(R.string.phone_number))
        binding.etWhatsApp.setText(profile.whatsApp ?: getString(R.string.phone_number_whats_app))
        binding.etTelegram.setText(profile.telegram ?: getString(R.string.telegram_name))
        binding.etInstagram.setText(profile.instagram ?: getString(R.string.instagram_yourname))
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