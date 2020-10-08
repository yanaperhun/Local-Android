package com.local.app.ui.fragments.profile

import android.content.Intent
import android.view.LayoutInflater
import com.local.app.BindableFragment
import com.local.app.databinding.FragmentProfileBinding
import com.local.app.ui.activities.event.CreateEventActivity

class ProfileFragment : BindableFragment<FragmentProfileBinding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentProfileBinding.inflate(inflater)
    }

    init {
//        binding.btnCreateEvent.setOnClickListener { showCreateEventStartFragment() }
    }

    private fun showCreateEventStartFragment() {
        startActivity(Intent(requireContext(), CreateEventActivity::class.java))
    }
}