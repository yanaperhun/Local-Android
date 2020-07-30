package com.local.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.local.app.R
import com.local.app.presentation.viewmodel.FeedViewModel
import com.local.app.presentation.viewmodel.main.MainActivityViewModel
import com.local.app.ui.feed.FeedListFragment
import com.local.app.ui.login.LoginDialog
import com.local.app.ui.login.LoginDialogCallback

class MainActivity : BaseActivity() {

    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)


        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main)
            showFragment(FeedListFragment(), true, R.id.container)
        }
    }

    fun onUserClick(view: View) {
        val loginDialog = LoginDialog()

        loginDialog.loginDialogCallback = object : LoginDialogCallback {

            override fun onVkSelected() {
            }

            override fun onInstagramSelected() {
            }

            override fun onGoogleSelected() {
            }

            override fun onEmailSelected() {
            }

            override fun onAuthSelected() {
            }

        }
        loginDialog.show(supportFragmentManager, "login_dialog")
    }

    fun onFilterClick(view: View) {
        FilterDialogFragment().show(supportFragmentManager, "filters_dialog")
    }

    class FilterDialogFragment : BottomSheetDialogFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        }

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View {

            // get the views and attach the listener
            return inflater.inflate(R.layout.dialog_filter, container, false)
        }

    }

}
