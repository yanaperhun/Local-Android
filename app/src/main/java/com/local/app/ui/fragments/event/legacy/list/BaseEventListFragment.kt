package com.local.app.ui.fragments.event.legacy.list

import android.view.LayoutInflater
import com.local.app.BindableFragment
import com.local.app.databinding.FragmentEventListBinding

open class BaseEventListFragment : BindableFragment<FragmentEventListBinding>() {
    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentEventListBinding.inflate(inflater)
    }



}