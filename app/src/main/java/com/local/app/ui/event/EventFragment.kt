package com.local.app.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.local.app.databinding.FragmentEventBinding
import com.local.app.databinding.FragmentLoginBinding
import com.local.app.presentation.viewmodel.EventViewModel
import com.local.app.ui.BaseFragment

class EventFragment : BaseFragment() {

    lateinit var binding: FragmentEventBinding
    lateinit var viewModel: EventViewModel
    var eventId: Int = 0

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentEventBinding.inflate(inflater)

        viewModel = ViewModelProviders
            .of(this)
            .get(EventViewModel::class.java)

        getDagger().feedComponent?.inject(this)
        return binding.root
    }

    public fun setEvent(id: Int) {
        eventId = id
        arguments?.putInt("EVENT_ID", eventId)
        refreshData()
    }

    private fun refreshData() {
        binding.event = viewModel.getEventById(eventId)
    }
}