package com.local.app.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.local.app.adapters.PhotoViewerAdapter
import com.local.app.databinding.FragmentPhotoViewerBinding
import com.local.app.presentation.viewmodel.PhotoViewerViewModel

class PhotoViewerFragment : Fragment() {

    private lateinit var binding: FragmentPhotoViewerBinding
    private var adapter: PhotoViewerAdapter? = null
    private lateinit var viewModel: PhotoViewerViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoViewerBinding.inflate(inflater);
        return binding.root;
    }

    override fun onStart() {
        super.onStart()

        if (adapter == null) {
            adapter = PhotoViewerAdapter(viewModel.getPhotos("1"))
        }
        binding.rvPhoto.adapter = adapter
    }
}