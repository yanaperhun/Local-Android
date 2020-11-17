package com.local.app.ui.fragments.feed.profile

import android.os.Bundle

class MyEventsFragment : BaseEventListFragment() {

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        log("call initUI in MyEventsFragment")
        viewModel.uiStateMyEvents.observe(viewLifecycleOwner, observer)
        viewModel.loadMyEvents()

    }
}