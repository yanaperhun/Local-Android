package com.local.app.ui.fragments.feed.profile

import android.os.Bundle

class LikedEventsFragment : BaseEventListFragment() {

    override fun initUI(state: Bundle?) {
        super.initUI(state)

        viewModel.uiStateLikedEvents.observe(viewLifecycleOwner, observer)
        viewModel.loadLikedEvents()
        log("call initUI in LikedEventsFragment")
    }


}