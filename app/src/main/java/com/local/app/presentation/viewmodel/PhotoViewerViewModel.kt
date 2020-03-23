package com.local.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.local.app.data.Photo

class PhotoViewerViewModel : ViewModel() {


    fun getPhotos(postId: String): List<Photo> {
        return emptyList();
    }

}