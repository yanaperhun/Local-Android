package com.local.app.ui.feed.state

import android.util.EventLog
import com.local.app.data.Event

sealed class FeedState {
    object Loading : FeedState()
    class Error(val throwable: Throwable) : FeedState()
    class Success(val event: List<Event>) : FeedState()
}