package com.local.app.ui.fragments.profile

import android.content.Intent
import android.view.LayoutInflater
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.local.app.BindableFragment
import com.local.app.databinding.FragmentProfileBinding
import com.local.app.ui.activities.event.CreateEventActivity
import com.local.app.ui.fragments.feed.profile.BaseEventListFragment
import com.local.app.ui.fragments.feed.profile.LikedEventsFragment
import com.local.app.ui.fragments.feed.profile.MyEventsFragment

class ProfileFragment : BindableFragment<FragmentProfileBinding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentProfileBinding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.btnCreateEvent.setOnClickListener { showCreateEventStartFragment() }
        binding.viewPager.adapter = object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): BaseEventListFragment =
                when (position) {
                    0 -> LikedEventsFragment()
                    1 -> MyEventsFragment()
                    else -> LikedEventsFragment()
                }
        }
    }

    private fun showCreateEventStartFragment() {
        startActivity(Intent(requireContext(), CreateEventActivity::class.java))
    }
}