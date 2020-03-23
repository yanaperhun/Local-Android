package com.local.app.api.response

import android.provider.CalendarContract
import com.local.app.data.Event

data class EventsFeedResponse(var data: List<Event>)