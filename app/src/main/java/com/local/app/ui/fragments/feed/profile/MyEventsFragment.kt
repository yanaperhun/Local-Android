package com.local.app.ui.fragments.feed.profile

import android.os.Bundle
import android.view.View

class MyEventsFragment : BaseEventListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMyEvents()
    }
}