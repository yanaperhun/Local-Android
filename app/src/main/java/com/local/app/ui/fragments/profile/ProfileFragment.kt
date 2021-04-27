package com.local.app.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.local.app.BindableFragment
import com.local.app.R
import com.local.app.data.Profile
import com.local.app.databinding.FragmentProfileBinding
import com.local.app.presentation.viewmodel.event.create.LoadProfileState
import com.local.app.presentation.viewmodel.profile.ProfileViewModel
import com.local.app.ui.activities.MainActivity
import com.local.app.ui.activities.event.CreateEventActivity
import com.local.app.ui.fragments.feed.profile.LikedEventsFragment
import com.local.app.ui.fragments.feed.profile.MyEventsFragment

class ProfileFragment : BindableFragment<FragmentProfileBinding>() {
    val viewModel: ProfileViewModel by viewModels()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentProfileBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        (requireActivity() as MainActivity).showProfileButton(false)
        binding.btnBack.setOnClickListener { requireActivity().finish() }
        binding.btnCreateEvent.setOnClickListener {
            startActivity(Intent(requireContext(), CreateEventActivity::class.java))
        }
        initViewPager()
        subscribeToViewModel()
    }

    override fun subscribeToViewModel() {
        super.subscribeToViewModel()
        viewModel.loadProfileState.observe(this, {
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

    private fun initUserData(profile: Profile) {
        showRoundImage(binding.ivAvatar, profile.getProfileImage()?.url?.getDefault() ?: "")
        binding.tvName.text = "${profile.firstName} ${profile.lastName}"
        initTabs()
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

    private fun initViewPager() {

    }
}