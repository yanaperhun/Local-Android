package com.local.app.ui.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.local.app.databinding.FragmentEventBinding
import com.local.app.databinding.ItemRvEventBinding
import com.local.app.presentation.viewmodel.EventViewModel
import com.local.app.ui.BaseFragment
import com.local.app.ui.event.state.EventState

class EventFragment : BaseFragment() {

    lateinit var binding: FragmentEventBinding
    lateinit var viewModel: EventViewModel

    companion object {
        fun create(eventId: Long): EventFragment {
            val fragment = EventFragment()
            val args = Bundle()
            args.putLong(EVENT_ID, eventId)
            fragment.arguments = args
            return fragment
        }
    }

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

    override fun onStart() {
        super.onStart()
        viewModel.eventState.observe(this, Observer {
            when (it) {
                is EventState.Success -> {
                    binding.event = it.event
                    binding.executePendingBindings()
                }
            }
        })
        refreshData()
    }

    private fun refreshData() {
        Log.d("Event id ", "" + arguments?.getLong(EVENT_ID, -1))
        arguments
            ?.getLong(EVENT_ID, -1)
            ?.let { viewModel.getEventById(it) }
    }
}

public const val EVENT_ID = "EVENT_ID"