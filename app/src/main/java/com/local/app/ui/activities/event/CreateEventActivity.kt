package com.local.app.ui.activities.event

import android.os.Bundle
import androidx.activity.viewModels
import com.local.app.R
import com.local.app.presentation.viewmodel.event.create.CreateEventViewModel
import com.local.app.ui.BaseActivity
import com.local.app.ui.fragments.event.create.CreateEventStepTitleFragment

class CreateEventActivity : BaseActivity() {

    private val viewModel: CreateEventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_create)
        showFragment(CreateEventStepTitleFragment(), true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}