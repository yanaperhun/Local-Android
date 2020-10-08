package com.local.app.ui.activities.user

import android.os.Bundle
import androidx.activity.viewModels
import com.local.app.R
import com.local.app.presentation.viewmodel.event.list.EventListViewModel
import com.local.app.ui.BaseActivity
import com.local.app.ui.fragments.profile.ProfileFragment

class UserActivity : BaseActivity() {

     val viewModel : EventListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        showFragment(ProfileFragment(), true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}