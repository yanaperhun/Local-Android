package com.local.app.ui.fragments.feed.profile

class LikedEventsFragment : BaseEventListFragment() {

    override fun initUI() {
        super.initUI()
        viewModel.uiStateLikedEvents.observe(viewLifecycleOwner, observer)
        viewModel.loadLikedEvents()
        log("call initUI in LikedEventsFragment")
    }


}