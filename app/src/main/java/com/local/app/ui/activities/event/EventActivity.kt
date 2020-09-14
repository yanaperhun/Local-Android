package com.local.app.ui.activities.event

import android.os.Bundle
import com.local.app.R
import com.local.app.ui.BaseActivity
import com.local.app.ui.fragments.event.EventFragment

class EventActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        val fragment =
            EventFragment.create(
                intent.getLongExtra(
                    EXTRAS_EVENT_ID,
                    -1))
        showFragment(fragment, true)
    }
}

public const val EXTRAS_EVENT_ID = "EVENT_ID"