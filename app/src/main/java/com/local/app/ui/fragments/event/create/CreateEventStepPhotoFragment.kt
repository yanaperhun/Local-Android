package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepPhotoBinding
import com.local.app.presentation.viewmodel.event.create.EventCreationState
import com.local.app.ui.adapters.PhotoPickerAdapter
import com.local.app.utils.BottomTopItemDecoration
import com.local.app.utils.Utils
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.File
import java.net.URI

class CreateEventStepPhotoFragment :
    BaseCreateEventFragment<FragmentCreateEventStepPhotoBinding>() {

    private var adapter = object : PhotoPickerAdapter() {
        override fun onAddBtnClick() {
            TedImagePicker
                .with(requireContext())
                .start { result ->
                    photos.add(result)
                    viewModel.uploadPhoto(File(URI(result.path)).absolutePath)
                    notifyDataSetChanged()
                }
        }

    }

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepPhotoBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.btnCreate.setOnClickListener { viewModel.createEvent() }
        binding.rvPhotos.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvPhotos.addItemDecoration(BottomTopItemDecoration(Utils
                                                                       .dpToPx(8)
                                                                       .toInt()))
        viewModel.eventCreationState.observe(this, Observer {
            if (it is EventCreationState.LOADING) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            when (it) {
                is EventCreationState.ERROR -> {
                    showErrorAlert(it.error.message)
                }
                is EventCreationState.SUCCESS -> {
                    showToast("Событие создано")
                }
            }
        })

        binding.rvPhotos.viewTreeObserver.addOnGlobalLayoutListener(object :
                                                                        ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //todo calculate size only once
                binding.rvPhotos.adapter = adapter
                binding.rvPhotos.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        viewModel.photoLoadingState.observe(this, Observer {
            adapter.notifyDataSetChanged()
            when (it) {
                is EventCreationState.ERROR -> {
                    showErrorAlert(it.error.message)
                }
                is EventCreationState.SUCCESS -> {
                    showToast("Событие создано")
                }

                is EventCreationState.LOADING -> {
                    showToast("Событие создано")
                }
            }
        })
    }

    override fun onNext() {
    }

    override fun onValidate(): Boolean {
        return false
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_6)
    }

}