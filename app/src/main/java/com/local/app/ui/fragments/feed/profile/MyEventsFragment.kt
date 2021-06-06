package com.local.app.ui.fragments.feed.profile

import android.os.Bundle
import com.local.app.R

class MyEventsFragment : BaseEventListFragment() {

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        log("call initUI in MyEventsFragment")
        viewModel.uiStateMyEvents.observe(viewLifecycleOwner, observer)
        viewModel.loadMyEvents()
        binding.tvEmpty.text = resources.getString(R.string.no_events_try_to_create)
    }
}