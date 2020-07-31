package com.local.app.ui.fragments.feed.state

import com.local.app.data.event.Event

sealed class FeedState {
    object Loading : FeedState()
    class Error(val throwable: Throwable) : FeedState()
    class Success(val event: List<Event>) : FeedState()
}