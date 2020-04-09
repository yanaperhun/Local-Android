package com.local.app.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.local.app.data.Event
import com.local.app.data.User
import com.local.app.databinding.FragmentFeedBinding
import com.local.app.presentation.viewmodel.FeedViewModel
import com.local.app.presentation.viewmodel.FeedViewModel.Callbacks
import com.local.app.ui.BaseFragment

class FeedListFragment : BaseFragment(), Callbacks {

    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding
    private lateinit var adapter: FeedRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders
            .of(this)
            .get(FeedViewModel::class.java)

        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.callbacks = this
        viewModel.loadFeed()
    }

    override fun onStop() {
        super.onStop()
        viewModel.callbacks = null
    }

    override var onUserLoaded = { u: List<Event> -> showFeed(u) }

    override var onError = { t: Throwable -> t.printStackTrace() }

    override fun onResume() {
        super.onResume()
        viewModel.eventsLD.observe(this, Observer { showFeed(it) })
    }

    private fun showFeed(it: List<Event>) {
        binding.rvEvents.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvEvents)
        binding.rvEvents.adapter = FeedRVAdapter(it)
        //        binding.rvEvents.isNestedScrollingEnabled = true
    }
}