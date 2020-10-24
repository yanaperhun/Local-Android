package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.databinding.FragmentCreateEventStep5Binding
import com.local.app.ui.BaseFragment

class CreateEventStep5Fragment : BaseCreateEventFragment<FragmentCreateEventStep5Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep5Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()

    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStep6Fragment()
    }

    override fun onNext() {
    }


}