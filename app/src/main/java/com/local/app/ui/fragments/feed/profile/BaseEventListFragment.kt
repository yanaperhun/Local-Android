package com.local.app.ui.fragments.feed.profile

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.local.app.BindableFragment
import com.local.app.databinding.FragmentEventListBinding
import com.local.app.presentation.viewmodel.event.list.EventListState
import com.local.app.presentation.viewmodel.event.list.EventListViewModel

abstract class BaseEventListFragment : BindableFragment<FragmentEventListBinding>() {

    protected val viewModel: EventListViewModel by activityViewModels()
    private val adapter = EventListRVAdapter()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentEventListBinding.inflate(inflater)
    }


    val observer = Observer<EventListState> {
        when (it) {
            is EventListState.Loading -> binding.progressBar.visibility =
                if (it.isLoading) View.VISIBLE else View.GONE

            is EventListState.Error -> showErrorAlert(it.error.message ?: "Ошибка")

            is EventListState.Success -> adapter.addEvents(it.events)

        }
    }

}