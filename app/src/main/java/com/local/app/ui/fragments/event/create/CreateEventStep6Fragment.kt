package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStep6Binding
import com.local.app.presentation.viewmodel.event.create.EventCreationState
import com.local.app.ui.BaseFragment

class CreateEventStep6Fragment : BaseCreateEventFragment<FragmentCreateEventStep6Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep6Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.btnCreate.setOnClickListener { viewModel.createEvent() }
        viewModel.eventCreationState.observe(this, Observer {
            if (it is EventCreationState.LOADING) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            when (it) {
                is EventCreationState.ERROR -> {
                    showErrorAlert(it.error.message)
                }
                is EventCreationState.SUCCESS -> {
                    showToast("Событие создано")
                }
            }
        })
    }

    override fun getNextFragment(): BaseFragment {
        return BaseFragment()
    }

    override fun onNext() {
    }

    override fun onValidate(): Boolean {
        return false
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_6)
    }

}