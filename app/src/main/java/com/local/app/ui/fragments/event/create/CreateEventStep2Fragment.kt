package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.databinding.FragmentCreateEventStep2Binding
import com.local.app.ui.BaseFragment

class CreateEventStep2Fragment : BaseCreateEventFragment<FragmentCreateEventStep2Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep2Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()

    }
    override fun getNextFragment(): BaseFragment {
        return CreateEventStep3Fragment()
    }


}