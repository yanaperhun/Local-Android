package com.local.app.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.local.app.databinding.FragmentFeedBinding
import com.local.app.presentation.viewmodel.FeedViewModel
import com.local.app.ui.BaseFragment

class FeedFragment : BaseFragment() {

    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders
            .of(this)
            .get(FeedViewModel::class.java)

        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()


    }
}