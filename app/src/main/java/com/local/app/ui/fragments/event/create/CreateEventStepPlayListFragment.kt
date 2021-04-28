package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.LayoutInflater
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepPlaylistBinding
import com.local.app.presentation.viewmodel.profile.StepConnectToFragment
import com.local.app.ui.BaseFragment
import com.local.app.ui.adapters.event.create.SoundLinksAdapter
import com.local.app.utils.SimpleTextWatcher

class CreateEventStepPlayListFragment : BaseCreateEventFragment<FragmentCreateEventStepPlaylistBinding>() {

    private val adapter = SoundLinksAdapter()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepPlaylistBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.etPlaylist.setText(viewModel
                                       .eventBuilder()
                                       .getSinglePlayList())
    }

    override fun getNextFragment(): BaseFragment {
        return StepConnectToFragment()
    }

    override fun onResume() {
        super.onResume()
        binding.etPlaylist.addTextChangedListener(playlistWatcher)
        focusET(binding.etPlaylist)
    }

    override fun onPause() {
        super.onPause()
        binding.etPlaylist.removeTextChangedListener(playlistWatcher)
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

    private val playlistWatcher = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            super.onTextChanged(s, start, before, count)
            if (s.isNullOrEmpty()) return
            viewModel
                .eventBuilder()
                .setSinglePlayList(binding.etPlaylist.text.toString())
        }
    }

}