package com.local.app.ui.fragments.feed.profile

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.local.app.BindableFragment
import com.local.app.databinding.FragmentEventListBinding
import com.local.app.presentation.viewmodel.event.list.EventListViewModel

abstract class BaseEventListFragment : BindableFragment<FragmentEventListBinding>() {

    val viewModel : EventListViewModel by activityViewModels()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentEventListBinding.inflate(inflater)
    }

}