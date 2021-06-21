package com.local.app.ui.fragments.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.local.app.R
import com.local.app.data.User
import com.local.app.data.event.Event
import com.local.app.databinding.FragmentEventFullBinding
import com.local.app.presentation.viewmodel.event.EventViewModel
import com.local.app.ui.BaseFragment
import com.local.app.ui.dialog.ConnectToDialog
import com.local.app.ui.fragments.event.state.EventState
import com.local.app.ui.photo.CommonRVEventElements
import com.local.app.utils.Utils
import java.net.URL

class EventDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentEventFullBinding
    lateinit var viewModel: EventViewModel

    companion object {
        fun create(eventId: Long): EventDetailsFragment {
            val fragment = EventDetailsFragment()
            val args = Bundle()
            args.putLong(EVENT_ID, eventId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentEventFullBinding.inflate(inflater)

        viewModel = ViewModelProviders
            .of(this)
            .get(EventViewModel::class.java)

        getDagger().feedComponent?.inject(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.eventState.observe(this, {
            when (it) {
                is EventState.Success -> showEvent(it.event)
                is EventState.Error -> it.throwable.printStackTrace()
            }
        })
        refreshData()

    }

    private fun showEvent(event: Event) {
        binding.event = event
        binding.executePendingBindings()
        binding.tvPrice.text = event.getFormattedPrice()
        CommonRVEventElements.buildTagsView(binding.cgTags, event.tags)
        CommonRVEventElements.showImages(binding.rvImages, event.pictures)

        val soundLink = event.getAnyPlaylist()

        if (soundLink.isEmpty()) {
            binding.groupSoundList.isVisible = false
        } else {
            if (URLUtil.isValidUrl(soundLink)) {
                binding.btnSoundcloud.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(soundLink))
                    startActivity(browserIntent)
                }
                binding.tvSingerName.text = URL(soundLink).host
            } else {
                binding.tvSingerName.text = soundLink
            }
        }


        event.creator.getAnyUserPicture()?.url?.md?.let {
            Utils.showRounderCornersImage(binding.ivCreator, it, Utils
                .dpToPx(8)
                .toInt())
        }
        binding.btnCloseEvent.setOnClickListener { requireActivity().finish() }
        binding.btnContact.setOnClickListener { connectWithCreator(event.creator) }

        bindContactsToViews(binding.btnContact, event)
    }

    private fun bindContactsToViews(btnContact: TextView, event: Event) {
        event.printConnectionsType()
        if (event.isConnectionTypeExist()) {
            btnContact.setOnClickListener { showConnectButtonDialog(event) }
        } else {
            btnContact.background = resources.getDrawable(R.drawable.rect_grey_light_stroke_18dp_corner)
        }

    }

    private fun showConnectButtonDialog(event: Event) {
        ConnectToDialog
            .create(event)
            .show(parentFragmentManager, "connect_to_dialog")
    }

    private fun connectWithCreator(creator: User) {

    }

    private fun refreshData() {
        Log.d("Event id ", "" + arguments?.getLong(EVENT_ID, -1))
        arguments
            ?.getLong(EVENT_ID, -1)
            ?.let { viewModel.getEventById(it) }
    }
}

public const val EVENT_ID = "EVENT_ID"