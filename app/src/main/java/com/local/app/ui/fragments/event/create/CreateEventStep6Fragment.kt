package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.local.app.databinding.FragmentCreateEventStep6Binding
import com.local.app.ui.BaseFragment

class CreateEventStep6Fragment : BaseCreateEventFragment<FragmentCreateEventStep6Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep6Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()

    }

    override fun getNextFragment(): BaseFragment {
        return BaseFragment()
    }

}