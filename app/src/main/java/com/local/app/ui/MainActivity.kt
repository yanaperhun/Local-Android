package com.local.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

        companion object {
            fun newInstance(): FilterDialogFragment {
                return FilterDialogFragment()
            }
        }
    }

}
