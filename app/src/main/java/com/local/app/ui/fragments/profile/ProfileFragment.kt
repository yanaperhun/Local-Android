package com.local.app.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.local.app.BindableFragment
import com.local.app.R
import com.local.app.data.Profile
import com.local.app.databinding.FragmentProfileBinding
import com.local.app.presentation.viewmodel.event.create.LoadProfileState
import com.local.app.presentation.viewmodel.profile.ProfileViewModel
import com.local.app.ui.activities.event.CreateEventActivity
import com.local.app.ui.fragments.feed.profile.LikedEventsFragment
import com.local.app.ui.fragments.feed.profile.MyEventsFragment
import com.local.app.ui.fragments.profile.settings.ProfileSettingsFragment

class ProfileFragment : BindableFragment<FragmentProfileBinding>() {
    val viewModel: ProfileViewModel by viewModels()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentProfileBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.btnBack.setOnClickListener { backStep() }
        binding.btnCreateEvent.setOnClickListener {
            startActivity(Intent(requireContext(), CreateEventActivity::class.java))
        }

        binding.btnMenu.setOnClickListener { showMenu(it) }
        initViewPager()
        subscribeToViewModel()
    }

    private fun showMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_profile)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btn_settings -> {
                    showSettingsFragment()
                    true
                }
                R.id.btn_logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showSettingsFragment() {
        showFragment(ProfileSettingsFragment(), true)
    }

    private fun logout() {
        viewModel.logout()
        backStep()
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
        binding.tvName.text = profile.getUserName()
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