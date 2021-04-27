package com.local.app.ui.fragments.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.local.app.data.event.Event
import com.local.app.databinding.FragmentFeedBinding
import com.local.app.presentation.viewmodel.feed.EventsFeedViewModel
import com.local.app.ui.BaseFragment
import com.local.app.ui.activities.MainActivity
import com.local.app.ui.activities.event.EXTRAS_EVENT_ID
import com.local.app.ui.activities.event.EventActivity
import com.local.app.ui.fragments.feed.state.FeedState

class EventsFeedFragment : BaseFragment() {

    private lateinit var viewModel: EventsFeedViewModel
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders
            .of(this)
            .get(EventsFeedViewModel::class.java)

        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).showProfileButton(true)
        viewModel.feedState.observe(this, {
            when (it) {
                is FeedState.Error -> it.throwable.printStackTrace()

                is FeedState.Loading -> showProgress()

                is FeedState.Success -> showFeed(it.event)
            }
        })

        if (viewModel.isFeedEmpty()) viewModel.loadFeed()
    }

    private fun showProgress() {

    }

    private fun showFeed(it: List<Event>) {
        binding.rvEvents.layoutManager = object :
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
            }

        binding.rvEvents.onFlingListener = null
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvEvents)

        binding.rvEvents.adapter = object : EventsFeedRVAdapter(it) {
            override fun onClicks(click: Clicks) {
                when (click) {
                    is Clicks.Dislike -> skipEvent(click.eventId)
                    is Clicks.Like -> likeEvent(click.eventId)
                    is Clicks.Event -> openEventScreen(click.eventId)
                }
            }
        }
    }


    private fun likeEvent(eventId: Long) {
        openEventScreen(eventId)
    }

    private fun openEventScreen(eventId: Long) {
        val i = Intent(context, EventActivity::class.java)
        i.putExtra(EXTRAS_EVENT_ID, eventId)
        startActivity(i)
    }

    private fun skipEvent(eventId: Long) {

    }
}