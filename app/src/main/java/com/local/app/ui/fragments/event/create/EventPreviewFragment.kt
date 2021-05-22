package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.databinding.FragmentPreviewEventBinding

class EventPreviewFragment : BaseCreateEventFragment<FragmentPreviewEventBinding>() {

    override fun onNext() {

    }

    override fun onValidate(): Boolean {
        return true
    }

    override fun getValidateMessage(): String {
        return ""
    }

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentPreviewEventBinding.inflate(inflater)
    }
}