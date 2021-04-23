package com.local.app.ui.fragments.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.local.app.BindableFragment
import com.local.app.R
import com.local.app.data.Profile
import com.local.app.databinding.FragmentProfileOldBinding
import com.local.app.presentation.viewmodel.event.create.CreateEventViewModel
import com.local.app.presentation.viewmodel.event.create.LoadProfileState
import com.local.app.ui.activities.event.CreateEventActivity
import com.local.app.ui.fragments.feed.profile.LikedEventsFragment
import com.local.app.ui.fragments.feed.profile.MyEventsFragment
import com.local.app.utils.Utils

class ProfileFragment : BindableFragment<FragmentProfileOldBinding>() {

    val viewModel: CreateEventViewModel by activityViewModels()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentProfileOldBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.btnCreateEvent.setOnClickListener { showCreateEventStartFragment() }
        binding.btnBack.setOnClickListener { requireActivity().finish() }
        initTabs()
        subscribeToViewModel()
    }

    override fun subscribeToViewModel() {
        super.subscribeToViewModel()
        viewModel.loadProfileState.observe(this, Observer {
            when (it) {
                is LoadProfileState.ERROR -> {
                    showErrorAlert(it.error.message)
                }
                is LoadProfileState.LOADING -> {

                }
                is LoadProfileState.SUCCESS -> {
                    initUserData(it.profile)
                }
            }
        })
        viewModel.loadProfile()
    }

    @SuppressLint("SetTextI18n")
    private fun initUserData(profile: Profile) {
        log(profile.toString())
        binding.title.text = "${profile.firstName} ${profile.lastName}"
        profile
            .getProfileImage()
            ?.let {
                showImage(binding.toolbarImage, it.url.lg)
                showRounderCornersImage(binding.ivAvatar, it.url.lg, Utils
                    .dpToPx(10)
                    .toInt())
            }

    }

    private fun initTabs() {
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = object :
            FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            override fun getItem(position: Int): Fragment {
                log("Create $position fragment")
                return when (position) {
                    0 -> LikedEventsFragment()
                    1 -> MyEventsFragment()
                    else -> LikedEventsFragment()
                }
            }

            override fun getCount(): Int = 2

            override fun getPageTitle(position: Int): CharSequence? {
                return if (position == 0) getString(R.string.liked) else getString(R.string.created)
            }
        }
    }

    private fun showCreateEventStartFragment() {
        startActivity(Intent(requireContext(), CreateEventActivity::class.java))
    }
}