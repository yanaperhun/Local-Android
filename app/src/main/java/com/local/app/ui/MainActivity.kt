package com.local.app.ui

import android.os.Bundle
import com.local.app.R
import com.local.app.ui.feed.FeedListFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main)
            showFragment(FeedListFragment(), true, R.id.container)
        }
    }

}
