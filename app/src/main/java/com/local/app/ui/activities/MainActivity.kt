package com.local.app.ui.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.local.app.R
import com.local.app.databinding.ActivityMainBinding
import com.local.app.presentation.viewmodel.main.MainActivityViewModel
import com.local.app.ui.BaseActivity
import com.local.app.ui.fragments.feed.EventsFeedFragment

class MainActivity : BaseActivity() {


    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders
            .of(this)
            .get(MainActivityViewModel::class.java)

        if (savedInstanceState == null) {
            showFragment(EventsFeedFragment(), true, R.id.container)
        }

    }
}
