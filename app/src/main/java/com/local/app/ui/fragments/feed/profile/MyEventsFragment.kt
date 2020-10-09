package com.local.app.ui.fragments.feed.profile

class MyEventsFragment : BaseEventListFragment() {

    override fun initUI() {
        super.initUI()
        log("call initUI in MyEventsFragment")
        viewModel.uiStateMyEvents.observe(viewLifecycleOwner, observer)
        viewModel.loadMyEvents()

    }
}