package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStep5Binding
import com.local.app.ui.BaseFragment
import com.local.app.ui.adapters.SpacesItemDecoration
import com.local.app.ui.adapters.event.create.SoundLinksAdapter

class CreateEventStep5Fragment : BaseCreateEventFragment<FragmentCreateEventStep5Binding>() {

    private val adapter = SoundLinksAdapter()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep5Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.rvLinks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLinks.addItemDecoration(SpacesItemDecoration(R.dimen.control_medium_margin))
        binding.rvLinks.adapter = adapter
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStep6Fragment()
    }

    override fun onNext() {
        //viewModel.eventBuilder().
    }

    override fun onValidate(): Boolean {
        return true
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_5)
    }

}