package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.databinding.FragmentCreateEventStep4Binding
import com.local.app.ui.BaseFragment

class CreateEventStep4Fragment : BaseCreateEventFragment<FragmentCreateEventStep4Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep4Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()

    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStep5Fragment()
    }

}