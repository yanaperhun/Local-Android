package com.local.app.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.local.app.ui.BaseFragment
import com.local.app.ui.adapters.PhotoViewerAdapter
import com.local.app.databinding.FragmentPhotoViewerBinding
import com.local.app.presentation.viewmodel.PhotoViewerViewModel

class PhotoViewerFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotoViewerBinding
    private lateinit var viewModel: PhotoViewerViewModel
    private var adapter: PhotoViewerAdapter? = null

    companion object {
        private const val EVENT_ID = "event_id"

        fun create(eventId: Int) = PhotoViewerFragment().apply {
            arguments = Bundle(2).apply {
                putInt(EVENT_ID, eventId)
            }
        }
    }

    init {
        getDagger().plusFeedComponent()
        getDagger().feedComponent?.inject(this);
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoViewerBinding.inflate(inflater);
        return binding.root;
    }

    override fun onStart() {
        super.onStart()

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvPhoto)
        if (adapter == null) {
            adapter = PhotoViewerAdapter(viewModel.getPhotos(arguments?.getInt(EVENT_ID)!!))
        }
        binding.rvPhoto.adapter = adapter
    }
}