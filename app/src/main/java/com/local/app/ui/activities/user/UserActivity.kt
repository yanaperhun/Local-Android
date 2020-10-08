package com.local.app.ui.activities.user

import android.os.Bundle
import com.local.app.R
import com.local.app.ui.BaseActivity
import com.local.app.ui.fragments.profile.ProfileFragment

class UserActivity : BaseActivity() {

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