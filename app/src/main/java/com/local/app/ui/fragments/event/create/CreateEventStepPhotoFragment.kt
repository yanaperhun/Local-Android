package com.local.app.ui.fragments.event.create

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CreateEventStepPhotoFragment :
    BaseCreateEventFragment<FragmentCreateEventStepPhotoBinding>() {

    private var adapter = object : PhotoPickerAdapter() {
        override fun onAddBtnClick() {
            chooseImage()
        }

    }

    private fun chooseImage() {
        TedImagePicker
            .with(requireContext())
            .start { result ->

                val bitmap = BitmapFactory.decodeStream(
                    requireContext().contentResolver.openInputStream(result))
                if (bitmap != null) {
                    val fileName = createFile(requireContext(), ".jpg").absolutePath
                    compress(fileName, bitmap)
                    viewModel.uploadPhoto(fileName)
                    adapter.photos.add(fileName)
                    adapter.notifyDataSetChanged()

                }

            }
    }

    private fun compress(outputFile: String, inputFile: Bitmap) {
        try {
            val out = FileOutputStream(outputFile)
            //            val bmp = BitmapFactory.decodeFile(inputFile)
            val result = inputFile.compress(Bitmap.CompressFormat.JPEG, 50, out) //100-best quality
            log("result : $result")
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createFile(context: Context, extension: String): File {
        val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.US)
        return File(context.filesDir, "IMG_${sdf.format(Date())}.$extension")
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

        adapter.setPhoto(viewModel.eventBuilder().pictures.map { it.uri })
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