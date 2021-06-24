package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.local.app.BindableFragment
import com.local.app.R
import com.local.app.presentation.viewmodel.event.create.CreateEventViewModel
import com.local.app.ui.BaseFragment

abstract class BaseCreateEventFragment<T : ViewBinding> : BindableFragment<T>() {

    val viewModel: CreateEventViewModel by activityViewModels()

    open fun getPrevFragment() {}

    open fun getNextFragment(): BaseFragment? {
        return null
    }

    open fun onNext() {
        getNextFragment()?.let {
            showFragment(it, true)
        }
    }
    abstract fun onValidate(): Boolean
    abstract fun getValidateMessage(): String

    override fun initUI(state: Bundle?) {
        super.initUI(state)

        binding.root
            .findViewById<View>(R.id.btn_next)
            ?.setOnClickListener { goNext() }
        binding.root
            .findViewById<ImageButton>(R.id.btn_close)
            .setOnClickListener { requireActivity().finish() }

        binding.root
            .findViewById<ImageButton>(R.id.btn_back)
            ?.setOnClickListener { backStep() }
    }

    private fun goNext() {
        if (onValidate()) {
            onNext()
        }
    }



    private fun showValidateMessage() {
        showInfoAlert(getValidateMessage())
    }
}